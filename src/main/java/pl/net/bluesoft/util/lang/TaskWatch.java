package pl.net.bluesoft.util.lang;

import org.apache.commons.lang3.time.StopWatch;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public class TaskWatch {
    public static String DEFAULT_HEADER_PATTERN = "TaskWatch \"{0}\" running time:\t{1}\n----------- Task summary -----------";
    public static String DEFAULT_ROW_PATTERN = "\t{0}\t\t{1}";

    private String name;
    private Map<String, StopWatch> watchMap = new HashMap<String, StopWatch>();
    private Set<String> watchesRunning = new HashSet<String>();
    private StopWatch totalWatch = new StopWatch();
    private boolean suspended = false;
    private String lastTaskName;

    private String headerPattern = DEFAULT_HEADER_PATTERN;
    private String rowPattern = DEFAULT_ROW_PATTERN;

    public TaskWatch(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderPattern() {
        return headerPattern;
    }

    public void setHeaderPattern(String headerPattern) {
        if (!Strings.hasText(headerPattern)) {
            throw new IllegalArgumentException("Header pattern cannot be empty!");
        }
        this.headerPattern = headerPattern;
    }

    public String getRowPattern() {
        return rowPattern;
    }

    public void setRowPattern(String rowPattern) {
        if (!Strings.hasText(rowPattern)) {
            throw new IllegalArgumentException("Row pattern cannot be empty!");
        }
        this.rowPattern = rowPattern;
    }

    public void start(String taskName) {
        if (watchesRunning.contains(taskName)) {
            throw new IllegalArgumentException("Watch for '" + taskName + "' already running");
        }
        if (!watchMap.containsKey(taskName)) {
            watchMap.put(taskName, new StopWatch());
        }
        StopWatch watch = watchMap.get(taskName);
        watch.reset();
        watch.start();

        if (watchesRunning.isEmpty()) {
            if (suspended) {
                totalWatch.resume();
            }
            else {
                totalWatch.start();
            }
        }
        watchesRunning.add(taskName);
        lastTaskName = taskName;
    }

    public long stop(String taskName) {
        StopWatch watch = watchMap.get(taskName);
        if (watch == null) {
            throw new IllegalArgumentException("No such task: " + taskName);
        }
        if (!watchesRunning.contains(taskName)) {
            throw new IllegalArgumentException("Watch for '" + taskName + "' is not running");
        }
        watch.stop();
        watchesRunning.remove(taskName);
        if (watchesRunning.isEmpty()) {
            totalWatch.suspend();
            suspended = true;
        }
        return watch.getTime();
    }

    public long stopLast() {
        if (lastTaskName == null) {
            throw new IllegalArgumentException("No task started yet!");
        }
        return stop(lastTaskName);
    }

    public void startLast() {
        if (lastTaskName == null) {
            throw new IllegalArgumentException("No task started yet!");
        }
        start(lastTaskName);
    }

    public long stopAll() {
        if (!watchesRunning.isEmpty()) {
            for (String taskName : watchesRunning) {
                watchMap.get(taskName).stop();
            }
            totalWatch.stop();
        }
        return totalWatch.getTime();
    }

    public <T> T watchTask(String taskName, Callable<T> task) throws Exception {
        start(taskName);
        try {
            return task.call();
        }
        finally {
            stop(taskName);
        }
    }

    public void watchTask(String taskName, Runnable task) {
        start(taskName);
        task.run();
        stop(taskName);
    }

    public String printSummary() {
        List<String> taskNames = new ArrayList<String>(watchMap.keySet());
        java.util.Collections.sort(taskNames);
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format(headerPattern, name, totalWatch.toString())).append("\n");
        for (String taskName : taskNames) {
            StopWatch watch = watchMap.get(taskName);
            sb.append(MessageFormat.format(rowPattern, taskName, watch.toString())).append("\n");
        }
        return sb.toString();
    }
}
