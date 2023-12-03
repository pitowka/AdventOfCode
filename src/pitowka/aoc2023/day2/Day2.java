package pitowka.aoc2023.day2;

import pitowka.aoc.InputFileLines;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {

    public static void main(String[] args) throws IOException {
        System.err.println(
            new InputFileLines("/day2.txt")
                .lines()
                .map(Game::new)
                .filter(g -> g.isPossible(Map.of(
                    "red", 12,
                    "green", 13,
                    "blue", 14)))
                .mapToInt(Game::id)
                .sum()
        );

        System.err.println(
            new InputFileLines("/day2.txt")
                .lines()
                .map(Game::new)
                .map(Game::minimum)
                .mapToInt(m -> m.values().stream().reduce(Math::multiplyExact).orElse(0))
                .sum()
        );

    }

    //Game 1: 7 blue, 6 green, 3 red; 3 red, 5 green, 1 blue; 1 red, 5 green, 8 blue; 3 red, 1 green, 5 blue
    public static class Game {
        private static final Pattern GAME_PATTERN = Pattern.compile("Game (\\d+): (.*;?)");

        private final Integer id;
        private final Collection<GameSet> gameSets;

        public Game(String game) {
            this(
                GAME_PATTERN
                    .matcher(game)
                    .results()
                    .map(m -> m.group(1))
                    .findFirst()
                    .map(Integer::valueOf)
                    .orElseThrow(),
                GAME_PATTERN
                    .matcher(game)
                    .results()
                    .map(m -> m.group(2))
                    .findFirst()
                    .orElseThrow()
            );
        }

        public Game(Integer id, String gameSets) {
            this(
                id,
                Arrays.stream(gameSets.split("; "))
                    .map(String::trim)
                    .map(GameSet::new)
                    .toList());
        }

        public Game(Integer id, Collection<GameSet> gameSets) {
            this.id = id;
            this.gameSets = gameSets;
        }

        public Integer id() {
            return this.id;
        }

        public boolean isPossible(Map<String, Integer> bag) {
            return gameSets.stream()
                .allMatch(s -> s.isPossible(bag));
        }

        public Map<String, Integer> minimum() {
            return this.gameSets.stream()
                .reduce(GameSet::commonMin)
                .map(GameSet::asMap)
                .orElseGet(Map::of);
        }
    }

    public static class GameSet {
        private final Collection<Cube> cubes;

        public GameSet(String cubes) {
            this(Arrays.stream(cubes.split(", "))
                .map(String::trim)
                .map(Cube::new)
                .toList());
        }

        public GameSet(Collection<Cube> cubes) {
            this.cubes = cubes;
        }

        public boolean isPossible(Map<String, Integer> bag) {
            return bag.entrySet().stream()
                .allMatch(e -> findByColor(e.getKey()).isCountSatisfying(e.getValue()));
        }

        private Cube findByColor(String color) {
            return this.cubes.stream()
                .filter(c -> c.is4Color(color))
                .findFirst()
                .orElseGet(() -> new Cube(0, color));
        }

        public GameSet commonMin(GameSet other) {
            return new GameSet(
                Stream.concat(
                        this.cubes.stream().map(Cube::color),
                        other.cubes.stream().map(Cube::color))
                    .collect(Collectors.toSet()).stream()
                    .map(c -> findByColor(c).commonMin(other.findByColor(c)))
                    .toList()
            );
        }

        public Map<String, Integer> asMap() {
            return this.cubes.stream()
                .map(c -> Map.entry(c.color, c.count))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    public static class Cube {
        private static final Pattern CUBE_PATTERN = Pattern.compile("(\\d+)\\s+(\\w+)");
        private final Integer count;
        private final String color;

        public Cube(String cube) {
            this(
                CUBE_PATTERN
                    .matcher(cube)
                    .results()
                    .map(m -> m.group(1))
                    .findFirst()
                    .map(Integer::valueOf)
                    .orElseThrow(),
                CUBE_PATTERN
                    .matcher(cube)
                    .results()
                    .map(m -> m.group(2))
                    .findFirst()
                    .orElseThrow()
            );
        }

        public Cube(Integer count, String color) {
            this.count = count;
            this.color = color;
        }

        public String color() {
            return this.color;
        }

        public boolean is4Color(String color) {
            return this.color.equals(color);
        }

        public boolean isCountSatisfying(Integer count) {
            return this.count <= count;
        }

        public Cube commonMin(Cube other) {
            return new Cube(Math.max(this.count, other.count), this.color);
        }
    }
}


//