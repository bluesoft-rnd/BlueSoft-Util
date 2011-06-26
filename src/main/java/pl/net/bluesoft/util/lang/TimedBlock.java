package pl.net.bluesoft.util.lang;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TimedBlock {
    private static final Logger log = Logger.getLogger(TimedBlock.class.getName());
    private String name;
    private Long start;

    public TimedBlock(String name) {
        this.name = name;
        start = System.currentTimeMillis();
    }

    protected void timeMe() {
        log.log(Level.FINER, "TimedBlock ["+name+"], Duration = " + (System.currentTimeMillis()-start)+"ms");
    }

    public static void timedBlock(TimedBlock block) {
        block.timeMe();
    }
}
