package StreamApi;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class Pratice {

    @Test
    public void identityTest() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // identity()를 활용하여 List를 Map으로 변환하시오.
        Map<String, String> map = names.stream()
                        .collect(Collectors.toMap(s->s, identity()));
        System.out.println("map = " + map);
    }

    @Test
    public void lambdaTest() {
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");

//        Map<String, String> collect = words.stream()
//                .collect(Collectors.toMap(identity(),identity()));

        Function<String, String> identity = s -> s;
        for (String word : words) {
            System.out.println("word = " + word);
        }


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> integers = numbers.stream()
                .map(identity())
                .collect(Collectors.toList());

        System.out.println("integers = " + integers);

        // 스트림을 사용하여 List<Integer>를 그대로 변환하시오.
    }

    @Test
    public void streamExample1() {
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");

        // 문자열의 길이를 저장하는 List<Integer> 생성
        List<Integer> collect = words.stream()
                .map(w -> w.length())
                .collect(Collectors.toList());

        System.out.println("collect = " + collect);

    }

    @Test
    public void streamExample2() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 짝수만 필터링하여 List<Integer> 생성
        List<Integer> integers = numbers.stream()
                .filter(s -> s % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("integers = " + integers);

    }

    @Test
    public void streamExample3() {
        List<String> words = Arrays.asList("apple", "kiwi", "banana", "cherry");

        // 문자열을 길이 기준으로 정렬하여 List<String> 생성
        words.stream()
                .sorted(Comparator.comparing(String::length))
                        .forEach(System.out::println);
    }

    @Test
    public void StreamExample4() {
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 4, 4, 5, 5, 6);

        // 중복을 제거한 List<Integer> 생성
        numbers.stream()
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void StreamExample5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 모든 숫자의 합을 구하기
        Integer integer = numbers.stream()
                .reduce(Integer::sum)
                .orElse(0);

        System.out.println("integer = " + integer);
    }

    @Test
    public void streamExample6() {
        List<String> words = Arrays.asList("apple", "banana", "avocado", "cherry", "blueberry");

        // 첫 글자를 기준으로 그룹화
        Map<Character, List<String>> collect = words.stream()
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
        System.out.println("collect = " + collect);

    }

    @Test
    public void streamExample7() {
        List<Integer> numbers = Arrays.asList(10, 20, 5, 15, 30, 25);

        // 가장 큰 값 찾기
        Integer integer = numbers.stream()
                .max(Comparator.naturalOrder())
                .orElse(-1);
        System.out.println("integer = " + integer);
    }

    @Test
    public void streamAdvanced1() {
        //다음 List<String>에서 길이가 5 이상인 첫 번째 문자열을 찾아라.
        //만약 존재하지 않으면 "없음"을 반환하라.
        List<String> words = Arrays.asList("a", "ab", "abc", "apple", "banana", "cherry");

        // 길이가 5 이상인, 첫 번째 문자열 찾기
        String s = words.stream()
                .filter(w -> w.length() > 4)
                .findFirst()
                .orElse("없음");
        System.out.println("s = " + s);
    }

    @Test
    public void streamAdvanced2() {
//        다음 List<String>에서 "java"라는 문자열을 포함하는 요소가 있는지 확인하라.
//        있으면 "존재함", 없으면 "없음"을 출력하라.

        List<String> words = Arrays.asList("spring", "hibernate", "java", "kotlin", "python");

        // "java" 포함 여부 확인
//        String s = words.stream()
//                .filter(w -> w.contains("java"))
//                .findAny()
//                .orElse("없음");
//        System.out.println("s = " + s);

        // boolean 으로 하는방법??
        boolean java = words.stream()
                .anyMatch(w -> w.contains("java"));
        System.out.println("java = " + java);

    }

    @Test
    public void streamAdvanced3() {
//        List<Integer>에서 가장 큰 숫자가 여러 개 있을 경우, 모든 숫자를 리스트로 반환하라.

        List<Integer> numbers = Arrays.asList(10, 30, 20, 30, 25, 30);

        // 최댓값이 여러 개일 경우 모두 출력
        List<Integer> integers = numbers.stream()
                .max(Comparator.naturalOrder())
                .map(max -> numbers.stream()
                        .filter(n -> max == n)
                        .collect(Collectors.toList())
                ).orElse(Collections.emptyList());

        System.out.println("integers = " + integers);
    }


    @Test
    public void streamAdvanced4() {
//        다음 List<String>을 문자열을 키로, 길이를 값으로 갖는 Map<String, Integer>로 변환하라.
//단, 중복된 키가 없도록 보장해야 한다.
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");

        // 문자열을 키, 길이를 값으로 하는 Map 생성
        Map<String, Integer> wordMap = words.stream()
                .collect(Collectors.toMap(w -> w, w -> w.length()));

        System.out.println("wordMap = " + wordMap);
    }

    @Test
    public void streamAdvanced5() {
//    다음과 같은 중첩 List<List<Integer>>에서 하나의 List<Integer>로 평탄화(flatten) 하여 변환하라.
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8)
        );

        List<Integer> collect = nestedList.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);
    }

    @Test
    public void streamAdvanced6() {
//        다음 코드에서 병렬 스트림(parallelStream())을 사용하여 연산을 최적화하라.
//        단, 합계 연산(reduce)을 병렬 처리할 때 주의할 점을 고려해야 한다.
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 1_000_000; i++) {
            numbers.add(i);
        }
        Integer integer = numbers.parallelStream()
                .reduce(0, Integer::sum);
        System.out.println("integer = " + integer);
    }

    @Test
    public void streamAdvanced7() {
    //다음 List<Integer>에서 짝수와 홀수를 분리하여 Map<Boolean, List<Integer>>로 저장하라.
    //true 키에는 짝수 목록
    //false 키에는 홀수 목록

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    // 짝수와 홀수를 분리하여 Map<Boolean, List<Integer>> 생성
        Map<Boolean, List<Integer>> collect = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0
                ));
        System.out.println("collect = " + collect);
    }


    }