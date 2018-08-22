package kmi.fpj.lecture02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import java.util.concurrent.TimeUnit;
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
        long temp = 0;
        long min = Long.MAX_VALUE;
        for (int i = 0; i < 100000; i ++) {
            long ts1 = System.nanoTime();
            testFunc.apply(data);

            long ts2 = System.nanoTime();
            temp = (ts2 - ts1);

        }
        return temp;
    }

    public static void testAndPrint(Collection<Integer>data){

        prepareData(data);

        long timeStream = runTestMin(data, StreamTest::testStream);
        long timeTraditional = runTestMin(data, StreamTest::testTraditionalLoop);
        long timeParallelStream = runTestMin(data, StreamTest::testParallelStream);

        System.out.println(data.getClass());
        System.out.println("traditional: " + TimeUnit.MILLISECONDS.convert(timeTraditional, TimeUnit.NANOSECONDS));
        System.out.println("stream:      " + TimeUnit.MILLISECONDS.convert(timeStream,TimeUnit.NANOSECONDS));
        System.out.println("p. stream:   " + TimeUnit.MILLISECONDS.convert(timeParallelStream,TimeUnit.NANOSECONDS));
        System.out.println();


    }

    public static void main(String[] args) throws IOException {

        Collection<Integer> data = new ArrayList<>();
        testAndPrint(data);
        data = new LinkedList<>();
        testAndPrint(data);
        data = new HashSet<>();
        testAndPrint(data);
        data = new TreeSet<>();
        testAndPrint(data);


        InputStream inputStream = new URL("ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz").openStream();

        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream));
        String nasa = bufferedReader.lines().limit(100000).collect(Collectors.joining("\n"));


/*
        Stream.of(nasa.split("\n"))
                .map(e -> e.split(" ")[0])
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(System.out::println);

        Stream.of(nasa.split("\n"))
                .map(e -> e.substring((e.indexOf(":") + 1),(e.indexOf(":") + 3)))
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(System.out::println);

        Stream.of(nasa.split("\n"))
                .filter(e -> e.split(" ")[e.split(" ").length-2].equals("404"))
                .map(e -> e.split(" ")[0])
                .distinct()
                .forEach(System.out::println);
*/
        Stream.of(nasa.split("\n"))
                .map(line -> line.substring(0,line.indexOf("[") + 3))
                .distinct()
                .collect(Collectors.groupingBy((line -> line.split("\\[")[1]),Collectors.counting()))
                .entrySet().stream()
                .forEach(System.out::println);



    }
}