package com.testreactive;

import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.Arrays;

public class ReactiveExample {

    public static void main(String[] args) {
        /*
         * Creating a new sequence
         */
        Flux<String> flux = Flux.just("A", "B", "C"); // just: Создает Flux, который эмитирует указанные элементы
        Flux<Integer> fromArray = Flux.fromArray(new Integer[]{1, 2, 3}); // fromArray: Создает Flux из массива
        Flux<Integer> fromIterable = Flux.fromIterable(Arrays.asList(4, 5, 6)); // fromIterable: Создает Flux из Iterable
        Flux<Integer> fromStream = Flux.fromStream(Arrays.asList(7, 8, 9).stream()); // fromStream: Создает Flux из Stream

        /*
         * Transforming an existing sequence
         */
        flux.map(String::toLowerCase) // map: Преобразует каждый элемент
                .flatMap(s -> Flux.just(s + "!")) // flatMap: Преобразует каждый элемент в Publisher и объединяет их
                .startWith("Start") // startWith: Добавляет указанные элементы в начало
                .concatWith(Flux.just("End")) // concatWith: Добавляет указанный Publisher в конец
                .subscribe(System.out::println); // Подписываемся, чтобы вывести элементы

        /*
         * Peeking into the sequence
         */
        fromArray.doOnNext(System.out::println) // doOnNext: Выполняет действие при эмиссии элемента
                .doOnComplete(() -> System.out.println("Ended")) // doOnComplete: Выполняет действие при завершении
                .doOnError(e -> System.err.println("Error: " + e)) // doOnError: Выполняет действие при ошибке
                .doOnCancel(() -> System.out.println("Сanceled")) // doOnCancel: Выполняет действие при отмене
                .subscribe(); // Подписываемся, чтобы запустить последовательность

        /*
         * Filtering the sequence
         */
        fromIterable.filter(i -> i % 2 == 0) // filter: Фильтрует элементы
                .distinct() // distinct: Исключает дубликаты
                .elementAt(0) // elementAt: Эмитирует n-й элемент
                .takeLast(1) // takeLast: Эмитирует последние n элементов
                .subscribe(System.out::println); // Подписываемся, чтобы вывести отфильтрованные результаты

        /*
         * Error handling
         */
        Flux.<String>error(new RuntimeException("Error!"))
                .onErrorReturn("Change") // onErrorReturn: Возвращает замену при ошибке
                .onErrorResume(e -> Flux.just("Returned")) // onErrorResume: Возобновляет с помощью резервного Publisher при ошибке
                .retry(1) // retry: Повторяет Publisher при ошибке
                .subscribe(System.out::println); // Подписываемся, чтобы увидеть результат обработки ошибок

        /*
         * Working with time
         */
        flux.elapsed() // elapsed: Измеряет время между эмиссиями
                .subscribe(System.out::println); // Подписываемся, чтобы увидеть время

        Flux.interval(Duration.ofSeconds(1)) // interval: Эмитирует элементы через регулярные интервалы времени
                .timestamp() // timestamp: Эмитирует элементы с меткой времени
                .timeout(Duration.ofSeconds(5)) // timeout: Генерирует TimeoutException, если эмиссия не происходит в течение указанного времени
                .subscribe(t -> System.out.println("Timestamped: " + t)); // Подписываемся, чтобы увидеть результаты времени

        /*
         * Splitting the stream
         */
        flux.buffer(2) // buffer: Буферизует элементы в списки указанного размера
                .subscribe(buffered -> System.out.println("Buffered: " + buffered)); // Подписываемся, чтобы увидеть буферизированные элементы

        flux.groupBy(String::length) // groupBy: Группирует элементы на основе функции классификации
                .flatMap(Flux::collectList) // Подписываемся на каждую подгруппу
                .subscribe(grouped -> System.out.println("Grouped: " + grouped));

        flux.window(2) // window: Разделяет элементы на Flux окна указанного размера
                .flatMap(Flux::collectList) // Подписываемся на каждое окно, чтобы собрать элементы
                .subscribe(window -> System.out.println("Window: " + window));

        /*
         * Returning to the synchronous world
         */
        flux.collectList() // collectList: Собирает элементы Flux в список
                .subscribe(list -> System.out.println("Elements: " + list)); // Подписываемся, чтобы вывести список

        /*
         * Multicasting
         */
        Flux<String> sharedFlux = flux.publish().refCount(2); // publish: Превращает Flux в ConnectableFlux для многоадресной рассылки
        sharedFlux.cache() // cache: Кэширует эмитируемые элементы
                .subscribe(s -> System.out.println("Subscriber 1: " + s));
        sharedFlux.subscribe(s -> System.out.println("Subscriber 2: " + s));
        sharedFlux.replay() // replay: Повторно эмитирует последовательность новым подписчикам
                .subscribe(s -> System.out.println("Replay Subscriber: " + s));
    }
}
