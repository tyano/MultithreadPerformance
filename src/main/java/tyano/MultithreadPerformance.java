package tyano;

import java.util.*;

public class MultithreadPerformance {
    public static final int _keyRange = 100;
    public static final int _threadCount = 300;
    public static final int _repeat = 100000;

    public static void main(String[] args) throws Exception {
        final long start = System.currentTimeMillis();
        final Map<String,Integer> map = new HashMap<>();

        Runtime.getRuntime().addShutdownHook(new Thread( new Runnable() {
            public void run() {
                System.out.println(System.currentTimeMillis() - start);
                System.out.println(map);
            }
        }));

        for(int i = 0; i < _threadCount; ++i ) {
            new Thread(new Runnable() {
                public void run() {
                    for(int k = 0; k < _repeat; ++k) {
                        Random random = new Random();
                        String key = "key" + random.nextInt(_keyRange);

                        synchronized(map) {
                            Integer value = map.get(key);
                            if(value != null) {
                                map.put( key, value + 1);
                            } else {
                                map.put( key, 1);
                            }
                        }
                    }
                }
            }).start();
        }
    }
}