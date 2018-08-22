package kmi.fpj.lecture02;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MostFrequentCollector<T> implements Collector<T, HashMap<T, Integer>, T> {

    @Override
    public Supplier<HashMap<T, Integer>> supplier() {
        return () -> new HashMap<>();
    }

    @Override
    public BiConsumer<HashMap<T, Integer>, T> accumulator() {
        return (map, value) -> {
            int freq = map.getOrDefault(value, 0);
            map.put(value, freq + 1);
        };
    }

    @Override
    public BinaryOperator<HashMap<T, Integer>> combiner() {
        return (map1, map2) -> { map1.putAll(map2); return map1; };
    }

    @Override
    public Function<HashMap<T, Integer>, T> finisher() {
        return map -> map.entrySet().stream()
                .max((a, b) -> a.getValue() - b.getValue())
                .map(e -> e.getKey())
                .orElse(null);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.singleton(Characteristics.UNORDERED);
    }
}