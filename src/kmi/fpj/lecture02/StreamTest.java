package kmi.fpj.lecture02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class StreamTest {

    public static int testStream(Collection<Integer> c) {
        return c.stream().mapToInt(i -> i * 3).filter(i -> (i % 2) == 0).sum();
    }

    public static int testParallelStream(Collection<Integer> c) {
        return c.parallelStream().mapToInt(i -> i * 3).filter(i -> (i % 2) == 0).sum();
    }

    public static int testTraditionalLoop(Collection<Integer> c) {
        int r = 0;
        for (int i : c) {
            int q = i * 3;
            if ((q % 2) == 0)
                r += q;
        }
        return r;
    }

    public static void prepareData(Collection<Integer> data) {
        for (int i = 0; i < 10000; i++)
            data.add(i);
    }

    public static long runTest(Collection<Integer> data, Function<Collection<Integer>, Integer> testFunc) {
        System.gc();
        long total = 0;

        for (int i = 0; i < 100000; i ++) {
            long ts1 = System.nanoTime();
            testFunc.apply(data);

            long ts2 = System.nanoTime();
            total += (ts2 - ts1);
        }
        return total;
    }
    public static long runTestMin(Collection<Integer> data, Function<Collection<Integer>, Integer> testFunc) {
        System.gc();

        long min = Long.MAX_VALUE;
        for (int i = 0; i < 100000; i ++) {
            long ts1 = System.nanoTime();
            testFunc.apply(data);
            long ts2 = System.nanoTime();

            min = Math.min(ts2 - ts1,min);

        }
        return min;
    }

    public static void testAndPrint(Collection<Integer>data){

        prepareData(data);

        long timeStream = runTestMin(data, StreamTest::testStream);
        long timeTraditional = runTestMin(data, StreamTest::testTraditionalLoop);
        long timeParallelStream = runTestMin(data, StreamTest::testParallelStream);

        System.out.println(data.getClass());
        System.out.println("traditional: " + timeTraditional);
        System.out.println("stream:      " + timeStream);
        System.out.println("p. stream:   " + timeParallelStream);
        System.out.println();


    }



    public static void nasaFirst(String[] nasaLines){
        Stream.of(nasaLines)
                .map(e -> e.split(" ")[0])
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(System.out::println);

    }
    public static void nasaSecond(String[] nasaLines){
        Stream.of(nasaLines)
                .map(e -> e.substring((e.indexOf(":") + 1),(e.indexOf(":") + 3)))
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(System.out::println);

    }
    public static void nasaThirt(String[] nasaLines){
        Stream.of(nasaLines)
                .filter(e -> e.split(" ")[e.split(" ").length-2].equals("404"))
                .map(e -> e.split(" ")[0])
                .distinct()
                .forEach(System.out::println);

    }
    public static int nasaFourth(String[] nasaLines){
        Stream.of(nasaLines)
                .map(line -> line.substring(0,line.indexOf("[") + 3))
                .distinct()
                .collect(Collectors.groupingBy((line -> line.split("\\[")[1]),Collectors.counting()))
                .entrySet().stream()
                .forEach(System.out::println);

        return 0;

    }

    public static int traditionalNasaFourth(String[] nasaLog){

        ArrayList<String> addresses =  new ArrayList<>();
        int[] dayCount = new int[31];
        for(String line : nasaLog){
            String adress = line.split(" ")[0];
            if(addresses.contains(adress)){
                continue;
            }
            addresses.add(adress);
            int day = Integer.parseInt(line.split("\\[")[1].substring(0,2));

            dayCount[day]++;
        }

        return 0;

    }

    public static long testFourth(String[] data, Function<String[], Integer> testFunc){
        System.gc();
        long total = 0;

        for (int i = 0; i < 1000; i ++) {
            long ts1 = System.nanoTime();
            testFunc.apply(data);

            long ts2 = System.nanoTime();
            total += (ts2 - ts1);
        }
        return total;

    }

    public static void main(String[] args) throws IOException {
        /*
        Collection<Integer> data = new ArrayList<>();
        testAndPrint(data);
        data = new LinkedList<>();
        testAndPrint(data);
        data = new HashSet<>();
        testAndPrint(data);
        data = new TreeSet<>();
        testAndPrint(data);
        */


        InputStream inputStream = new URL("ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz").openStream();

        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream));
        String nasa = bufferedReader.lines().limit(100000).collect(Collectors.joining("\n"));
        String[] nasaLines = nasa.split("\n");

        nasaFourth(nasaLines);



        System.out.println(testFourth(nasaLines, nasaLog -> traditionalNasaFourth(nasaLog)));
        System.out.println(testFourth(nasaLines, nasaLog -> nasaFourth(nasaLog)));





        //System.out.println(nasa);

        //System.out.println(endTime-startTime);



    }
}