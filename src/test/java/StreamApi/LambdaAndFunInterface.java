package StreamApi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaAndFunInterface {

    @Test
    public void testMethodReferenceLength() {
        Function<String, Integer> function = (str) -> str.length();
        Integer length = function.apply("Hello world");

        Assertions.assertEquals(11, length); // 결과를 검증하는 코드 추가

        Function<String, Integer> function1 = String::length;
        Integer hello = function1.apply("hello");

        Assertions.assertEquals(5,hello);

    }

    @Test
    public void testMethodReferencePrintln() {
        // 일반 메소드 참조하여 Consumer 선언
        Consumer<String> c = System.out::println;
        c.accept("hello world");

        // 메소드 참조를 통해 Consumer 를 매개변수로 받는 forEach 쉽게 사용 가능
        List<String> list = Arrays.asList("red", "orange", "yellow", "green", "blue");
        list.forEach(System.out::println);
    }

    @Test
    public void testMethodReferenceStatic() {
        Predicate<Boolean> p = Objects::isNull; // isNull 실제로 static 으로 구현되어 있음
    }

    @Test
    public void testReduce() {
        OptionalInt reduce = IntStream.range(1, 3)
                .reduce( (a, b) -> {
                    return Integer.sum(a, b);
                });
        System.out.println("reduce = " + reduce.getAsInt());

        int reduce1 = IntStream.range(1, 3)
                .reduce(5, (a, b) -> {
                    return Integer.sum(a, b);
                });
        System.out.println("reduce1 = " + reduce1);

    }
    @Test
    public void testReduce2() {
        Integer combinerWasCalled = 10 + Stream.of(1, 2, 3)
                .parallel()
                .reduce(0, Integer::sum, (a, b) -> {
                    System.out.println("combiner was called");
                    return a + b;
                });
        System.out.println("combinerWasCalled = " + combinerWasCalled);
    }

    @Test
    public void testStreamSeq() {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("commonPool = " + commonPool);
    }

    @Test
    public void parallel() {
        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());

        System.out.println("일반 스트림 실행:");
        numbers.stream()
                .forEach(num -> System.out.println(Thread.currentThread().getName() + " - " + num));

        System.out.println("\n병렬 스트림 실행:");
        numbers.parallelStream()
                .forEach(num -> System.out.println(Thread.currentThread().getName() + " - " + num));

        System.out.println("--------forEach----------");
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");

        names.parallelStream()
                .forEach(System.out::println);  // 실행할 때마다 출력 순서가 다름

        System.out.println("--------forEachOrdered----------");
        names.parallelStream()
                .forEachOrdered(System.out::println);
    }

    @Test
    public void testThreadSafety() {
        List<Integer> list = new ArrayList<>();

        IntStream
                .range(1, 1000)
                .parallel()
                .forEach(list::add); // 문제 발생 가능!

        System.out.println(list.size()); // 예상보다 작은 크기가 나올 수 있음

        List<Integer> list2 = IntStream.range(1, 1000)
                .parallel()
                .boxed()
                .collect(Collectors.toList()); // 안전한 리스트 생성
        // Stream 작업한 결과를 List로 반환

        System.out.println(list2.size());
    }

    @Test
    public void testParallelPerformance() {
        List<String> names = List.of("Alice", "Bob", "Charlie", "David", "Eve");

        long start = System.nanoTime();
        names.stream().map(String::toUpperCase).forEach(System.out::println);
        long end = System.nanoTime();
        System.out.println("일반 스트림 실행 시간: " + (end - start) + " ns");

        start = System.nanoTime();
        names.parallelStream().map(String::toUpperCase).forEach(System.out::println);
        end = System.nanoTime();
        System.out.println("병렬 스트림 실행 시간: " + (end - start) + " ns");

    }
}
