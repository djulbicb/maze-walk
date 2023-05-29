package com.example.mazewalk.old;

import java.util.Random;

public class PerlinNoiseGenerator {

    private static final int DEFAULT_OCTAVES = 1;
    private static final double DEFAULT_PERSISTENCE = 100.0;
    private static final double DEFAULT_LACUNARITY = 100.0;
    private static final int DEFAULT_SEED = 0;

    public static double[][] generatePerlinNoise(int width, int height) {
        return generatePerlinNoise(width, height, DEFAULT_OCTAVES, DEFAULT_PERSISTENCE, DEFAULT_LACUNARITY, DEFAULT_SEED);
    }

    public static double[][] generatePerlinNoise(int width, int height, int octaves, double persistence, double lacunarity, int seed) {
        double[][] noise = new double[width][height];
        Random rng = new Random(seed);

        double frequency = 1.0;
        double amplitude = 1.0;
        double maxPossibleValue = 1.0;

        // Generate octave noise layers
        for (int octave = 0; octave < octaves; octave++) {
            double[][] octaveNoise = new double[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    double sampleX = x / frequency;
                    double sampleY = y / frequency;

                    double perlinValue = getSmoothNoise(sampleX, sampleY, rng) * amplitude;
                    octaveNoise[x][y] = perlinValue;
                }
            }

            maxPossibleValue += amplitude;
            amplitude *= persistence;
            frequency *= lacunarity;

            // Combine octave noise layers
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    noise[x][y] += octaveNoise[x][y];
                }
            }
        }

        // Normalize values to range [-1, 1]
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                noise[x][y] /= maxPossibleValue;
            }
        }

        return noise;
    }

    private static double getSmoothNoise(double x, double y, Random rng) {
        double corners = (getNoise(x - 1, y - 1, rng) + getNoise(x + 1, y - 1, rng) +
                getNoise(x - 1, y + 1, rng) + getNoise(x + 1, y + 1, rng)) / 16.0;
        double sides = (getNoise(x - 1, y, rng) + getNoise(x + 1, y, rng) +
                getNoise(x, y - 1, rng) + getNoise(x, y + 1, rng)) / 8.0;
        double center = getNoise(x, y, rng) / 4.0;
        return corners + sides + center;
    }

    private static double getNoise(double x, double y, Random rng) {
        rng.setSeed((long)(x * 8191 + y * 16249));
        return rng.nextDouble() * 2 - 1;
    }
}
