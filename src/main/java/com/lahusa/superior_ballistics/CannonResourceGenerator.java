package com.lahusa.superior_ballistics;

import net.minecraft.util.Identifier;

public class CannonResourceGenerator {

    private static final String blockStatePattern =
            //region Block State Pattern
            """
            {
                "variants": {
                    "angle=0,facing=north": { "model": "#NAMESPACE:block/#PATH_0", "uvlock": true },
                    "angle=0,facing=east":  { "model": "#NAMESPACE:block/#PATH_0", "y":  90, "uvlock": true },
                    "angle=0,facing=south": { "model": "#NAMESPACE:block/#PATH_0", "y": 180, "uvlock": true },
                    "angle=0,facing=west":  { "model": "#NAMESPACE:block/#PATH_0", "y": 270, "uvlock": true },
                    "angle=1,facing=north": { "model": "#NAMESPACE:block/#PATH_1", "uvlock": true },
                    "angle=1,facing=east":  { "model": "#NAMESPACE:block/#PATH_1", "y":  90, "uvlock": true },
                    "angle=1,facing=south": { "model": "#NAMESPACE:block/#PATH_1", "y": 180, "uvlock": true },
                    "angle=1,facing=west":  { "model": "#NAMESPACE:block/#PATH_1", "y": 270, "uvlock": true },
                    "angle=2,facing=north": { "model": "#NAMESPACE:block/#PATH_2", "uvlock": true },
                    "angle=2,facing=east":  { "model": "#NAMESPACE:block/#PATH_2", "y":  90, "uvlock": true },
                    "angle=2,facing=south": { "model": "#NAMESPACE:block/#PATH_2", "y": 180, "uvlock": true },
                    "angle=2,facing=west":  { "model": "#NAMESPACE:block/#PATH_2", "y": 270, "uvlock": true },
                    "angle=3,facing=north": { "model": "#NAMESPACE:block/#PATH_3", "uvlock": true },
                    "angle=3,facing=east":  { "model": "#NAMESPACE:block/#PATH_3", "y":  90, "uvlock": true },
                    "angle=3,facing=south": { "model": "#NAMESPACE:block/#PATH_3", "y": 180, "uvlock": true },
                    "angle=3,facing=west":  { "model": "#NAMESPACE:block/#PATH_3", "y": 270, "uvlock": true }
                }
            }
            """;
            //endregion

    private static final String blockModelPattern0 =
            //region Model Variant 0
            """
            {
            	"credit": "Made with Blockbench",
            	"textures": {
            		"0": "block/anvil",
            		"1": "block/blackstone_top",
            		"2": "#PLANK_NAMESPACE:block/#PLANK_PATH",
            		"3": "#LOG_NAMESPACE:block/#LOG_PATH",
            		"particle": "block/anvil"
            	},
            	"elements": [
            		{
            			"from": [3, 1, 15],
            			"to": [4, 6, 16],
            			"faces": {
            				"north": {"uv": [8, 3, 9, 8], "texture": "#0"},
            				"east": {"uv": [6, 9, 7, 14], "texture": "#0"},
            				"south": {"uv": [3, 2, 4, 7], "texture": "#0"},
            				"west": {"uv": [2, 7, 3, 12], "texture": "#0"},
            				"up": {"uv": [5, 6, 6, 7], "texture": "#0"},
            				"down": {"uv": [4, 13, 5, 14], "texture": "#0"}
            			}
            		},
            		{
            			"from": [3, 1, 0],
            			"to": [4, 9, 1],
            			"faces": {
            				"north": {"uv": [0, 8, 1, 16], "texture": "#0"},
            				"east": {"uv": [1, 2, 2, 10], "texture": "#0"},
            				"south": {"uv": [5, 5, 6, 13], "texture": "#0"},
            				"west": {"uv": [3, 2, 4, 10], "texture": "#0"},
            				"up": {"uv": [5, 3, 6, 4], "texture": "#0"},
            				"down": {"uv": [3, 14, 4, 15], "texture": "#0"}
            			}
            		},
            		{
            			"from": [3, 8, 1],
            			"to": [4, 9, 6],
            			"faces": {
            				"north": {"uv": [0, 7, 1, 8], "texture": "#2"},
            				"east": {"uv": [1, 7, 6, 8], "texture": "#2"},
            				"south": {"uv": [11, 7, 12, 8], "texture": "#2"},
            				"west": {"uv": [6, 7, 11, 8], "texture": "#2"},
            				"up": {"uv": [11, 7, 16, 8], "rotation": 90, "texture": "#2"},
            				"down": {"uv": [1, 8, 6, 9], "rotation": 90, "texture": "#2"}
            			}
            		},
            		{
            			"from": [3, 6, 1],
            			"to": [4, 8, 12],
            			"faces": {
            				"north": {"uv": [12, 9, 13, 11], "texture": "#2"},
            				"east": {"uv": [1, 9, 12, 11], "texture": "#2"},
            				"south": {"uv": [0, 9, 1, 11], "texture": "#2"},
            				"west": {"uv": [1, 9, 12, 11], "texture": "#2"},
            				"up": {"uv": [1, 8, 12, 9], "rotation": 90, "texture": "#2"},
            				"down": {"uv": [1, 11, 12, 12], "rotation": 90, "texture": "#2"}
            			}
            		},
            		{
            			"from": [3, 1, 1],
            			"to": [4, 6, 15],
            			"faces": {
            				"north": {"uv": [15, 11, 16, 16], "texture": "#2"},
            				"east": {"uv": [1, 11, 15, 16], "texture": "#2"},
            				"south": {"uv": [0, 11, 1, 16], "texture": "#2"},
            				"west": {"uv": [1, 11, 15, 16], "texture": "#2"},
            				"up": {"uv": [1, 10, 15, 11], "rotation": 90, "texture": "#2"},
            				"down": {"uv": [1, 10, 15, 11], "rotation": 90, "texture": "#2"}
            			}
            		},
            		{
            			"from": [1, 2, 3],
            			"to": [3, 3, 4],
            			"faces": {
            				"north": {"uv": [4, 6, 6, 7], "texture": "#1"},
            				"east": {"uv": [8, 7, 9, 8], "texture": "#1"},
            				"south": {"uv": [8, 11, 10, 12], "texture": "#1"},
            				"west": {"uv": [10, 6, 11, 7], "texture": "#1"},
            				"up": {"uv": [7, 4, 9, 5], "texture": "#1"},
            				"down": {"uv": [4, 8, 6, 9], "texture": "#1"}
            			}
            		},
            		{
            			"from": [1.5, 1, 1],
            			"to": [2.5, 4, 2],
            			"faces": {
            				"north": {"uv": [0, 0, 1, 3], "texture": "#3"},
            				"east": {"uv": [6, 4, 7, 7], "texture": "#2"},
            				"south": {"uv": [9, 8, 10, 11], "texture": "#2"},
            				"west": {"uv": [9, 4, 10, 7], "texture": "#2"},
            				"up": {"uv": [0, 0, 1, 1], "texture": "#3"},
            				"down": {"uv": [0, 0, 1, 1], "texture": "#3"}
            			}
            		},
            		{
            			"from": [1.5, 0, 2],
            			"to": [2.5, 5, 5],
            			"faces": {
            				"north": {"uv": [15, 0, 16, 5], "texture": "#3"},
            				"east": {"uv": [1, 12, 6, 15], "rotation": 90, "texture": "#2"},
            				"south": {"uv": [6, 5, 7, 10], "texture": "#3"},
            				"west": {"uv": [6, 12, 11, 15], "rotation": 90, "texture": "#2"},
            				"up": {"uv": [11, 8, 12, 11], "texture": "#3"},
            				"down": {"uv": [9, 0, 10, 3], "texture": "#3"}
            			}
            		},
            		{
            			"from": [1.5, 1, 5],
            			"to": [2.5, 4, 6],
            			"faces": {
            				"north": {"uv": [5, 8, 6, 11], "texture": "#2"},
            				"east": {"uv": [9, 4, 10, 7], "texture": "#2"},
            				"south": {"uv": [5, 0, 6, 3], "texture": "#3"},
            				"west": {"uv": [5, 4, 6, 7], "texture": "#2"},
            				"up": {"uv": [7, 11, 8, 12], "texture": "#3"},
            				"down": {"uv": [9, 1, 10, 2], "texture": "#3"}
            			}
            		},
            		{
            			"from": [1, 2, 12],
            			"to": [3, 3, 13],
            			"faces": {
            				"north": {"uv": [4, 6, 6, 7], "texture": "#1"},
            				"east": {"uv": [8, 7, 9, 8], "texture": "#1"},
            				"south": {"uv": [8, 11, 10, 12], "texture": "#1"},
            				"west": {"uv": [10, 6, 11, 7], "texture": "#1"},
            				"up": {"uv": [7, 4, 9, 5], "texture": "#1"},
            				"down": {"uv": [4, 8, 6, 9], "texture": "#1"}
            			}
            		},
            		{
            			"from": [1.5, 1, 10],
            			"to": [2.5, 4, 11],
            			"faces": {
            				"north": {"uv": [0, 0, 1, 3], "texture": "#3"},
            				"east": {"uv": [6, 4, 7, 7], "texture": "#2"},
            				"south": {"uv": [9, 8, 10, 11], "texture": "#2"},
            				"west": {"uv": [7, 4, 8, 7], "texture": "#2"},
            				"up": {"uv": [0, 0, 1, 1], "texture": "#3"},
            				"down": {"uv": [0, 0, 1, 1], "texture": "#3"}
            			}
            		},
            		{
            			"from": [1.5, 0, 11],
            			"to": [2.5, 5, 14],
            			"faces": {
            				"north": {"uv": [15, 0, 16, 5], "texture": "#3"},
            				"east": {"uv": [2, 11, 7, 14], "rotation": 90, "texture": "#2"},
            				"south": {"uv": [6, 5, 7, 10], "texture": "#3"},
            				"west": {"uv": [6, 12, 11, 15], "rotation": 90, "texture": "#2"},
            				"up": {"uv": [11, 8, 12, 11], "texture": "#3"},
            				"down": {"uv": [9, 0, 10, 3], "texture": "#3"}
            			}
            		},
            		{
            			"from": [1.5, 1, 14],
            			"to": [2.5, 4, 15],
            			"faces": {
            				"north": {"uv": [5, 8, 6, 11], "texture": "#2"},
            				"east": {"uv": [9, 4, 10, 7], "texture": "#2"},
            				"south": {"uv": [5, 0, 6, 3], "texture": "#3"},
            				"west": {"uv": [5, 4, 6, 7], "texture": "#2"},
            				"up": {"uv": [7, 11, 8, 12], "texture": "#3"},
            				"down": {"uv": [9, 1, 10, 2], "texture": "#3"}
            			}
            		},
            		{
            			"from": [12, 1, 15],
            			"to": [13, 6, 16],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [9, 3, 8, 8], "texture": "#0"},
            				"east": {"uv": [3, 7, 2, 12], "texture": "#0"},
            				"south": {"uv": [4, 2, 3, 7], "texture": "#0"},
            				"west": {"uv": [7, 9, 6, 14], "texture": "#0"},
            				"up": {"uv": [6, 6, 5, 7], "texture": "#0"},
            				"down": {"uv": [5, 13, 4, 14], "texture": "#0"}
            			}
            		},
            		{
            			"from": [12, 1, 0],
            			"to": [13, 9, 1],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [1, 8, 0, 16], "texture": "#0"},
            				"east": {"uv": [4, 2, 3, 10], "texture": "#0"},
            				"south": {"uv": [6, 5, 5, 13], "texture": "#0"},
            				"west": {"uv": [2, 2, 1, 10], "texture": "#0"},
            				"up": {"uv": [6, 3, 5, 4], "texture": "#0"},
            				"down": {"uv": [4, 14, 3, 15], "texture": "#0"}
            			}
            		},
            		{
            			"from": [12, 8, 1],
            			"to": [13, 9, 6],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [1, 7, 0, 8], "texture": "#2"},
            				"east": {"uv": [11, 7, 6, 8], "texture": "#2"},
            				"south": {"uv": [12, 7, 11, 8], "texture": "#2"},
            				"west": {"uv": [6, 7, 1, 8], "texture": "#2"},
            				"up": {"uv": [11, 8, 16, 7], "rotation": 90, "texture": "#2"},
            				"down": {"uv": [1, 9, 6, 8], "rotation": 90, "texture": "#2"}
            			}
            		},
            		{
            			"from": [12, 6, 1],
            			"to": [13, 8, 12],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [13, 9, 12, 11], "texture": "#2"},
            				"east": {"uv": [12, 9, 1, 11], "texture": "#2"},
            				"south": {"uv": [1, 9, 0, 11], "texture": "#2"},
            				"west": {"uv": [12, 9, 1, 11], "texture": "#2"},
            				"up": {"uv": [1, 9, 12, 8], "rotation": 90, "texture": "#2"},
            				"down": {"uv": [1, 12, 12, 11], "rotation": 90, "texture": "#2"}
            			}
            		},
            		{
            			"from": [12, 1, 1],
            			"to": [13, 6, 15],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [16, 11, 15, 16], "texture": "#2"},
            				"east": {"uv": [15, 11, 1, 16], "texture": "#2"},
            				"south": {"uv": [1, 11, 0, 16], "texture": "#2"},
            				"west": {"uv": [15, 11, 1, 16], "texture": "#2"},
            				"up": {"uv": [1, 11, 15, 10], "rotation": 90, "texture": "#2"},
            				"down": {"uv": [1, 11, 15, 10], "rotation": 90, "texture": "#2"}
            			}
            		},
            		{
            			"from": [13, 2, 3],
            			"to": [15, 3, 4],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [6, 6, 4, 7], "texture": "#1"},
            				"east": {"uv": [11, 6, 10, 7], "texture": "#1"},
            				"south": {"uv": [10, 11, 8, 12], "texture": "#1"},
            				"west": {"uv": [9, 7, 8, 8], "texture": "#1"},
            				"up": {"uv": [9, 4, 7, 5], "texture": "#1"},
            				"down": {"uv": [6, 8, 4, 9], "texture": "#1"}
            			}
            		},
            		{
            			"from": [13.5, 1, 1],
            			"to": [14.5, 4, 2],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [1, 0, 0, 3], "texture": "#3"},
            				"east": {"uv": [8, 4, 7, 7], "texture": "#2"},
            				"south": {"uv": [10, 8, 9, 11], "texture": "#2"},
            				"west": {"uv": [7, 4, 6, 7], "texture": "#2"},
            				"up": {"uv": [1, 0, 0, 1], "texture": "#3"},
            				"down": {"uv": [1, 0, 0, 1], "texture": "#3"}
            			}
            		},
            		{
            			"from": [13.5, 0, 2],
            			"to": [14.5, 5, 5],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [16, 0, 15, 5], "texture": "#3"},
            				"east": {"uv": [7, 15, 12, 12], "rotation": 90, "texture": "#2"},
            				"south": {"uv": [7, 5, 6, 10], "texture": "#3"},
            				"west": {"uv": [2, 15, 7, 12], "rotation": 90, "texture": "#2"},
            				"up": {"uv": [12, 8, 11, 11], "texture": "#3"},
            				"down": {"uv": [10, 0, 9, 3], "texture": "#3"}
            			}
            		},
            		{
            			"from": [13.5, 1, 5],
            			"to": [14.5, 4, 6],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [6, 8, 5, 11], "texture": "#2"},
            				"east": {"uv": [6, 4, 5, 7], "texture": "#2"},
            				"south": {"uv": [6, 0, 5, 3], "texture": "#3"},
            				"west": {"uv": [10, 4, 9, 7], "texture": "#2"},
            				"up": {"uv": [8, 11, 7, 12], "texture": "#3"},
            				"down": {"uv": [10, 1, 9, 2], "texture": "#3"}
            			}
            		},
            		{
            			"from": [13, 2, 12],
            			"to": [15, 3, 13],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [6, 6, 4, 7], "texture": "#1"},
            				"east": {"uv": [11, 6, 10, 7], "texture": "#1"},
            				"south": {"uv": [10, 11, 8, 12], "texture": "#1"},
            				"west": {"uv": [9, 7, 8, 8], "texture": "#1"},
            				"up": {"uv": [9, 4, 7, 5], "texture": "#1"},
            				"down": {"uv": [6, 8, 4, 9], "texture": "#1"}
            			}
            		},
            		{
            			"from": [13.5, 1, 10],
            			"to": [14.5, 4, 11],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [1, 0, 0, 3], "texture": "#3"},
            				"east": {"uv": [8, 4, 7, 7], "texture": "#2"},
            				"south": {"uv": [10, 8, 9, 11], "texture": "#2"},
            				"west": {"uv": [7, 4, 6, 7], "texture": "#2"},
            				"up": {"uv": [1, 0, 0, 1], "texture": "#3"},
            				"down": {"uv": [1, 0, 0, 1], "texture": "#3"}
            			}
            		},
            		{
            			"from": [13.5, 0, 11],
            			"to": [14.5, 5, 14],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [16, 0, 15, 5], "texture": "#3"},
            				"east": {"uv": [6, 15, 11, 12], "rotation": 90, "texture": "#2"},
            				"south": {"uv": [7, 5, 6, 10], "texture": "#3"},
            				"west": {"uv": [1, 15, 6, 12], "rotation": 90, "texture": "#2"},
            				"up": {"uv": [12, 8, 11, 11], "texture": "#3"},
            				"down": {"uv": [10, 0, 9, 3], "texture": "#3"}
            			}
            		},
            		{
            			"from": [13.5, 1, 14],
            			"to": [14.5, 4, 15],
            			"rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
            			"faces": {
            				"north": {"uv": [6, 8, 5, 11], "texture": "#2"},
            				"east": {"uv": [6, 4, 5, 7], "texture": "#2"},
            				"south": {"uv": [6, 0, 5, 3], "texture": "#3"},
            				"west": {"uv": [10, 4, 9, 7], "texture": "#2"},
            				"up": {"uv": [8, 11, 7, 12], "texture": "#3"},
            				"down": {"uv": [10, 1, 9, 2], "texture": "#3"}
            			}
            		},
            		{
            			"from": [4, 1, 0],
            			"to": [12, 2, 16],
            			"faces": {
            				"north": {"uv": [8, 7, 16, 8], "texture": "#2"},
            				"east": {"uv": [0, 11, 16, 12], "texture": "#2"},
            				"south": {"uv": [0, 9, 8, 10], "texture": "#2"},
            				"west": {"uv": [0, 3, 16, 4], "texture": "#2"},
            				"up": {"uv": [0, 0, 8, 16], "texture": "#2"},
            				"down": {"uv": [8, 0, 16, 16], "texture": "#2"}
            			}
            		},
            		{
            			"from": [4, 2, 15],
            			"to": [12, 5, 16],
            			"faces": {
            				"north": {"uv": [8, 6, 16, 9], "texture": "#2"},
            				"east": {"uv": [13, 6, 14, 9], "texture": "#2"},
            				"south": {"uv": [0, 6, 8, 9], "texture": "#2"},
            				"west": {"uv": [11, 6, 12, 9], "texture": "#2"},
            				"up": {"uv": [0, 5, 8, 6], "texture": "#2"},
            				"down": {"uv": [0, 9, 8, 10], "texture": "#2"}
            			}
            		},
            		{
            			"from": [4, 2, 0],
            			"to": [12, 5, 1],
            			"faces": {
            				"north": {"uv": [8, 4, 16, 7], "texture": "#2"},
            				"east": {"uv": [12, 4, 13, 7], "texture": "#2"},
            				"south": {"uv": [0, 4, 8, 7], "texture": "#2"},
            				"west": {"uv": [1, 4, 2, 7], "texture": "#2"},
            				"up": {"uv": [3, 3, 11, 4], "texture": "#2"},
            				"down": {"uv": [3, 7, 11, 8], "texture": "#2"}
            			}
            		},
            		{
            			"from": [4, 5, 0],
            			"to": [6, 6, 1],
            			"faces": {
            				"north": {"uv": [13, 11, 15, 12], "texture": "#2"},
            				"east": {"uv": [13, 11, 14, 12], "texture": "#2"},
            				"south": {"uv": [13, 11, 15, 12], "texture": "#2"},
            				"west": {"uv": [13, 11, 14, 12], "texture": "#2"},
            				"up": {"uv": [13, 11, 15, 12], "texture": "#2"},
            				"down": {"uv": [13, 11, 15, 12], "texture": "#2"}
            			}
            		},
            		{
            			"from": [10, 5, 0],
            			"to": [12, 6, 1],
            			"faces": {
            				"north": {"uv": [7, 7, 9, 8], "texture": "#2"},
            				"east": {"uv": [7, 7, 8, 8], "texture": "#2"},
            				"south": {"uv": [7, 7, 9, 8], "texture": "#2"},
            				"west": {"uv": [7, 7, 8, 8], "texture": "#2"},
            				"up": {"uv": [7, 7, 9, 8], "texture": "#2"},
            				"down": {"uv": [7, 7, 9, 8], "texture": "#2"}
            			}
            		},
            		{
            			"from": [11, 6, 0],
            			"to": [12, 7, 1],
            			"faces": {
            				"north": {"uv": [3, 3, 4, 4], "texture": "#2"},
            				"east": {"uv": [8, 3, 9, 4], "texture": "#2"},
            				"south": {"uv": [7, 3, 8, 4], "texture": "#2"},
            				"west": {"uv": [6, 3, 7, 4], "texture": "#2"},
            				"up": {"uv": [5, 3, 6, 4], "texture": "#2"},
            				"down": {"uv": [4, 3, 5, 4], "texture": "#2"}
            			}
            		},
            		{
            			"from": [4, 6, 0],
            			"to": [5, 7, 1],
            			"faces": {
            				"north": {"uv": [0, 0, 1, 1], "texture": "#2"},
            				"east": {"uv": [0, 0, 1, 1], "texture": "#2"},
            				"south": {"uv": [0, 0, 1, 1], "texture": "#2"},
            				"west": {"uv": [0, 0, 1, 1], "texture": "#2"},
            				"up": {"uv": [0, 0, 1, 1], "texture": "#2"},
            				"down": {"uv": [0, 0, 1, 1], "texture": "#2"}
            			}
            		},
            		{
            			"from": [6, 6, 4],
            			"to": [10, 10, 14],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 4, 4], "texture": "#0"},
            				"east": {"uv": [0, 0, 10, 4], "texture": "#0"},
            				"south": {"uv": [0, 0, 4, 4], "texture": "#0"},
            				"west": {"uv": [0, 0, 10, 4], "texture": "#0"},
            				"up": {"uv": [0, 0, 4, 10], "texture": "#0"},
            				"down": {"uv": [0, 0, 4, 10], "texture": "#0"}
            			}
            		},
            		{
            			"from": [6.5, 6.5, 0],
            			"to": [9.5, 9.5, 4],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 3, 3], "texture": "#0"},
            				"east": {"uv": [0, 0, 4, 3], "texture": "#0"},
            				"south": {"uv": [0, 0, 3, 3], "texture": "#0"},
            				"west": {"uv": [0, 0, 4, 3], "texture": "#0"},
            				"up": {"uv": [0, 0, 3, 4], "texture": "#0"},
            				"down": {"uv": [0, 0, 3, 4], "texture": "#0"}
            			}
            		},
            		{
            			"from": [6.75, 6.75, -4],
            			"to": [9.25, 9.25, -3],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [1, 9, 3.5, 11.5], "texture": "#1"},
            				"east": {"uv": [0, 0, 1, 2.5], "texture": "#0"},
            				"south": {"uv": [0, 0, 2.5, 2.5], "texture": "#0"},
            				"west": {"uv": [0, 0, 1, 2.5], "texture": "#0"},
            				"up": {"uv": [0, 0, 2.5, 1], "texture": "#0"},
            				"down": {"uv": [0, 0, 2.5, 1], "texture": "#0"}
            			}
            		},
            		{
            			"from": [7, 7, -3],
            			"to": [9, 9, 0],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 2, 2], "texture": "#0"},
            				"east": {"uv": [0, 0, 3, 2], "texture": "#0"},
            				"south": {"uv": [0, 0, 2, 2], "texture": "#0"},
            				"west": {"uv": [0, 0, 3, 2], "texture": "#0"},
            				"up": {"uv": [0, 0, 2, 3], "texture": "#0"},
            				"down": {"uv": [0, 0, 2, 3], "texture": "#0"}
            			}
            		},
            		{
            			"from": [9, 6, -5],
            			"to": [10, 10, -4],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 1, 4], "texture": "#0"},
            				"east": {"uv": [0, 0, 1, 4], "texture": "#0"},
            				"south": {"uv": [0, 0, 1, 4], "texture": "#0"},
            				"west": {"uv": [0, 0, 1, 4], "texture": "#0"},
            				"up": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"down": {"uv": [0, 0, 1, 1], "texture": "#0"}
            			}
            		},
            		{
            			"from": [6, 6, -5],
            			"to": [7, 10, -4],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 1, 4], "texture": "#0"},
            				"east": {"uv": [0, 0, 1, 4], "texture": "#0"},
            				"south": {"uv": [0, 0, 1, 4], "texture": "#0"},
            				"west": {"uv": [0, 0, 1, 4], "texture": "#0"},
            				"up": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"down": {"uv": [0, 0, 1, 1], "texture": "#0"}
            			}
            		},
            		{
            			"from": [7, 9, -5],
            			"to": [9, 10, -4],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"east": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"south": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"up": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"down": {"uv": [0, 0, 2, 1], "texture": "#0"}
            			}
            		},
            		{
            			"from": [7, 6, -5],
            			"to": [9, 7, -4],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"east": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"south": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"up": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"down": {"uv": [0, 0, 2, 1], "texture": "#0"}
            			}
            		},
            		{
            			"from": [10, 7, 8],
            			"to": [12, 8, 9],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"east": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"south": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"up": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"down": {"uv": [0, 0, 2, 1], "texture": "#0"}
            			}
            		},
            		{
            			"from": [4, 7, 8],
            			"to": [6, 8, 9],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"east": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"south": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"west": {"uv": [0, 0, 1, 1], "texture": "#0"},
            				"up": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"down": {"uv": [0, 0, 2, 1], "texture": "#0"}
            			}
            		},
            		{
            			"from": [7, 7, 14],
            			"to": [9, 9, 15],
            			"rotation": {"angle": 0, "axis": "y", "origin": [8, 7.5, 8.5]},
            			"faces": {
            				"north": {"uv": [0, 0, 2, 2], "texture": "#0"},
            				"east": {"uv": [0, 0, 1, 2], "texture": "#0"},
            				"south": {"uv": [0, 0, 2, 2], "texture": "#0"},
            				"west": {"uv": [0, 0, 1, 2], "texture": "#0"},
            				"up": {"uv": [0, 0, 2, 1], "texture": "#0"},
            				"down": {"uv": [0, 0, 2, 1], "texture": "#0"}
            			}
            		}
            	],
            	"display": {
            		"thirdperson_righthand": {
            			"rotation": [114.25, 0, 0],
            			"translation": [-6, 0.25, 0.5]
            		},
            		"ground": {
            			"translation": [0, 4.5, 0]
            		},
            		"gui": {
            			"rotation": [30, 30, 0],
            			"translation": [0.25, 1, 0],
            			"scale": [0.8, 0.8, 0.8]
            		},
            		"head": {
            			"translation": [0, 13.5, 0],
            			"scale": [1.2, 1.2, 1.2]
            		}
            	},
            	"groups": [
            		{
            			"name": "Left",
            			"origin": [0, 0, 0],
            			"color": 0,
            			"children": [
            				{
            					"name": "Side_Left",
            					"origin": [0, 0, 0],
            					"color": 0,
            					"children": [0, 1, 2, 3, 4]
            				},
            				{
            					"name": "Wheel_Front_Left",
            					"origin": [0, 0, 0],
            					"color": 0,
            					"children": [5, 6, 7, 8]
            				},
            				{
            					"name": "Wheel_Back_Left",
            					"origin": [0, 0, 0],
            					"color": 0,
            					"children": [9, 10, 11, 12]
            				}
            			]
            		},
            		{
            			"name": "Right",
            			"origin": [0, 0, 0],
            			"color": 0,
            			"children": [
            				{
            					"name": "Side_Right",
            					"origin": [0, 0, 0],
            					"color": 0,
            					"children": [13, 14, 15, 16, 17]
            				},
            				{
            					"name": "Wheel_Front_Right",
            					"origin": [0, 0, 0],
            					"color": 0,
            					"children": [18, 19, 20, 21]
            				},
            				{
            					"name": "Wheel_Back_Right",
            					"origin": [0, 0, 0],
            					"color": 0,
            					"children": [22, 23, 24, 25]
            				}
            			]
            		},
            		{
            			"name": "Middle",
            			"origin": [8, 8, 8],
            			"color": 0,
            			"children": [26, 27, 28, 29, 30, 31, 32]
            		},
            		{
            			"name": "Cannon",
            			"origin": [0, 0, 0],
            			"color": 0,
            			"children": [33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43]
            		}
            	]
            }
            """;
            //endregion

    private static final String blockModelPattern1 =
            //region Model Variant 1
            """
            {
                "credit": "Made with Blockbench",
                "textures": {
                    "0": "block/anvil",
                    "1": "block/blackstone_top",
                    "2": "#PLANK_NAMESPACE:block/#PLANK_PATH",
            		"3": "#LOG_NAMESPACE:block/#LOG_PATH",
                    "particle": "block/anvil"
                },
                "elements": [
                    {
                        "from": [3, 1, 15],
                        "to": [4, 6, 16],
                        "faces": {
                            "north": {"uv": [8, 3, 9, 8], "texture": "#0"},
                            "east": {"uv": [6, 9, 7, 14], "texture": "#0"},
                            "south": {"uv": [3, 2, 4, 7], "texture": "#0"},
                            "west": {"uv": [2, 7, 3, 12], "texture": "#0"},
                            "up": {"uv": [5, 6, 6, 7], "texture": "#0"},
                            "down": {"uv": [4, 13, 5, 14], "texture": "#0"}
                        }
                    },
                    {
                        "from": [3, 1, 0],
                        "to": [4, 9, 1],
                        "faces": {
                            "north": {"uv": [0, 8, 1, 16], "texture": "#0"},
                            "east": {"uv": [1, 2, 2, 10], "texture": "#0"},
                            "south": {"uv": [5, 5, 6, 13], "texture": "#0"},
                            "west": {"uv": [3, 2, 4, 10], "texture": "#0"},
                            "up": {"uv": [5, 3, 6, 4], "texture": "#0"},
                            "down": {"uv": [3, 14, 4, 15], "texture": "#0"}
                        }
                    },
                    {
                        "from": [3, 8, 1],
                        "to": [4, 9, 6],
                        "faces": {
                            "north": {"uv": [0, 7, 1, 8], "texture": "#2"},
                            "east": {"uv": [1, 7, 6, 8], "texture": "#2"},
                            "south": {"uv": [11, 7, 12, 8], "texture": "#2"},
                            "west": {"uv": [6, 7, 11, 8], "texture": "#2"},
                            "up": {"uv": [11, 7, 16, 8], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 8, 6, 9], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [3, 6, 1],
                        "to": [4, 8, 12],
                        "faces": {
                            "north": {"uv": [12, 9, 13, 11], "texture": "#2"},
                            "east": {"uv": [1, 9, 12, 11], "texture": "#2"},
                            "south": {"uv": [0, 9, 1, 11], "texture": "#2"},
                            "west": {"uv": [1, 9, 12, 11], "texture": "#2"},
                            "up": {"uv": [1, 8, 12, 9], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 11, 12, 12], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [3, 1, 1],
                        "to": [4, 6, 15],
                        "faces": {
                            "north": {"uv": [15, 11, 16, 16], "texture": "#2"},
                            "east": {"uv": [1, 11, 15, 16], "texture": "#2"},
                            "south": {"uv": [0, 11, 1, 16], "texture": "#2"},
                            "west": {"uv": [1, 11, 15, 16], "texture": "#2"},
                            "up": {"uv": [1, 10, 15, 11], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 10, 15, 11], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [1, 2, 3],
                        "to": [3, 3, 4],
                        "faces": {
                            "north": {"uv": [4, 6, 6, 7], "texture": "#1"},
                            "east": {"uv": [8, 7, 9, 8], "texture": "#1"},
                            "south": {"uv": [8, 11, 10, 12], "texture": "#1"},
                            "west": {"uv": [10, 6, 11, 7], "texture": "#1"},
                            "up": {"uv": [7, 4, 9, 5], "texture": "#1"},
                            "down": {"uv": [4, 8, 6, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [1.5, 1, 1],
                        "to": [2.5, 4, 2],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 3], "texture": "#3"},
                            "east": {"uv": [6, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [9, 8, 10, 11], "texture": "#2"},
                            "west": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#3"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 0, 2],
                        "to": [2.5, 5, 5],
                        "faces": {
                            "north": {"uv": [15, 0, 16, 5], "texture": "#3"},
                            "east": {"uv": [1, 12, 6, 15], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [6, 5, 7, 10], "texture": "#3"},
                            "west": {"uv": [6, 12, 11, 15], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [11, 8, 12, 11], "texture": "#3"},
                            "down": {"uv": [9, 0, 10, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 1, 5],
                        "to": [2.5, 4, 6],
                        "faces": {
                            "north": {"uv": [5, 8, 6, 11], "texture": "#2"},
                            "east": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "south": {"uv": [5, 0, 6, 3], "texture": "#3"},
                            "west": {"uv": [5, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [7, 11, 8, 12], "texture": "#3"},
                            "down": {"uv": [9, 1, 10, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1, 2, 12],
                        "to": [3, 3, 13],
                        "faces": {
                            "north": {"uv": [4, 6, 6, 7], "texture": "#1"},
                            "east": {"uv": [8, 7, 9, 8], "texture": "#1"},
                            "south": {"uv": [8, 11, 10, 12], "texture": "#1"},
                            "west": {"uv": [10, 6, 11, 7], "texture": "#1"},
                            "up": {"uv": [7, 4, 9, 5], "texture": "#1"},
                            "down": {"uv": [4, 8, 6, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [1.5, 1, 10],
                        "to": [2.5, 4, 11],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 3], "texture": "#3"},
                            "east": {"uv": [6, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [9, 8, 10, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 8, 7], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#3"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 0, 11],
                        "to": [2.5, 5, 14],
                        "faces": {
                            "north": {"uv": [15, 0, 16, 5], "texture": "#3"},
                            "east": {"uv": [2, 11, 7, 14], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [6, 5, 7, 10], "texture": "#3"},
                            "west": {"uv": [6, 12, 11, 15], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [11, 8, 12, 11], "texture": "#3"},
                            "down": {"uv": [9, 0, 10, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 1, 14],
                        "to": [2.5, 4, 15],
                        "faces": {
                            "north": {"uv": [5, 8, 6, 11], "texture": "#2"},
                            "east": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "south": {"uv": [5, 0, 6, 3], "texture": "#3"},
                            "west": {"uv": [5, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [7, 11, 8, 12], "texture": "#3"},
                            "down": {"uv": [9, 1, 10, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [12, 1, 15],
                        "to": [13, 6, 16],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [9, 3, 8, 8], "texture": "#0"},
                            "east": {"uv": [3, 7, 2, 12], "texture": "#0"},
                            "south": {"uv": [4, 2, 3, 7], "texture": "#0"},
                            "west": {"uv": [7, 9, 6, 14], "texture": "#0"},
                            "up": {"uv": [6, 6, 5, 7], "texture": "#0"},
                            "down": {"uv": [5, 13, 4, 14], "texture": "#0"}
                        }
                    },
                    {
                        "from": [12, 1, 0],
                        "to": [13, 9, 1],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 8, 0, 16], "texture": "#0"},
                            "east": {"uv": [4, 2, 3, 10], "texture": "#0"},
                            "south": {"uv": [6, 5, 5, 13], "texture": "#0"},
                            "west": {"uv": [2, 2, 1, 10], "texture": "#0"},
                            "up": {"uv": [6, 3, 5, 4], "texture": "#0"},
                            "down": {"uv": [4, 14, 3, 15], "texture": "#0"}
                        }
                    },
                    {
                        "from": [12, 8, 1],
                        "to": [13, 9, 6],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 7, 0, 8], "texture": "#2"},
                            "east": {"uv": [11, 7, 6, 8], "texture": "#2"},
                            "south": {"uv": [12, 7, 11, 8], "texture": "#2"},
                            "west": {"uv": [6, 7, 1, 8], "texture": "#2"},
                            "up": {"uv": [11, 8, 16, 7], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 9, 6, 8], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [12, 6, 1],
                        "to": [13, 8, 12],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [13, 9, 12, 11], "texture": "#2"},
                            "east": {"uv": [12, 9, 1, 11], "texture": "#2"},
                            "south": {"uv": [1, 9, 0, 11], "texture": "#2"},
                            "west": {"uv": [12, 9, 1, 11], "texture": "#2"},
                            "up": {"uv": [1, 9, 12, 8], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 12, 12, 11], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [12, 1, 1],
                        "to": [13, 6, 15],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 11, 15, 16], "texture": "#2"},
                            "east": {"uv": [15, 11, 1, 16], "texture": "#2"},
                            "south": {"uv": [1, 11, 0, 16], "texture": "#2"},
                            "west": {"uv": [15, 11, 1, 16], "texture": "#2"},
                            "up": {"uv": [1, 11, 15, 10], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 11, 15, 10], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [13, 2, 3],
                        "to": [15, 3, 4],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 6, 4, 7], "texture": "#1"},
                            "east": {"uv": [11, 6, 10, 7], "texture": "#1"},
                            "south": {"uv": [10, 11, 8, 12], "texture": "#1"},
                            "west": {"uv": [9, 7, 8, 8], "texture": "#1"},
                            "up": {"uv": [9, 4, 7, 5], "texture": "#1"},
                            "down": {"uv": [6, 8, 4, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [13.5, 1, 1],
                        "to": [14.5, 4, 2],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 0, 0, 3], "texture": "#3"},
                            "east": {"uv": [8, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [10, 8, 9, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [1, 0, 0, 1], "texture": "#3"},
                            "down": {"uv": [1, 0, 0, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 0, 2],
                        "to": [14.5, 5, 5],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 0, 15, 5], "texture": "#3"},
                            "east": {"uv": [7, 15, 12, 12], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [7, 5, 6, 10], "texture": "#3"},
                            "west": {"uv": [2, 15, 7, 12], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [12, 8, 11, 11], "texture": "#3"},
                            "down": {"uv": [10, 0, 9, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 1, 5],
                        "to": [14.5, 4, 6],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 8, 5, 11], "texture": "#2"},
                            "east": {"uv": [6, 4, 5, 7], "texture": "#2"},
                            "south": {"uv": [6, 0, 5, 3], "texture": "#3"},
                            "west": {"uv": [10, 4, 9, 7], "texture": "#2"},
                            "up": {"uv": [8, 11, 7, 12], "texture": "#3"},
                            "down": {"uv": [10, 1, 9, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13, 2, 12],
                        "to": [15, 3, 13],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 6, 4, 7], "texture": "#1"},
                            "east": {"uv": [11, 6, 10, 7], "texture": "#1"},
                            "south": {"uv": [10, 11, 8, 12], "texture": "#1"},
                            "west": {"uv": [9, 7, 8, 8], "texture": "#1"},
                            "up": {"uv": [9, 4, 7, 5], "texture": "#1"},
                            "down": {"uv": [6, 8, 4, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [13.5, 1, 10],
                        "to": [14.5, 4, 11],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 0, 0, 3], "texture": "#3"},
                            "east": {"uv": [8, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [10, 8, 9, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [1, 0, 0, 1], "texture": "#3"},
                            "down": {"uv": [1, 0, 0, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 0, 11],
                        "to": [14.5, 5, 14],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 0, 15, 5], "texture": "#3"},
                            "east": {"uv": [6, 15, 11, 12], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [7, 5, 6, 10], "texture": "#3"},
                            "west": {"uv": [1, 15, 6, 12], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [12, 8, 11, 11], "texture": "#3"},
                            "down": {"uv": [10, 0, 9, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 1, 14],
                        "to": [14.5, 4, 15],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 8, 5, 11], "texture": "#2"},
                            "east": {"uv": [6, 4, 5, 7], "texture": "#2"},
                            "south": {"uv": [6, 0, 5, 3], "texture": "#3"},
                            "west": {"uv": [10, 4, 9, 7], "texture": "#2"},
                            "up": {"uv": [8, 11, 7, 12], "texture": "#3"},
                            "down": {"uv": [10, 1, 9, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [4, 1, 0],
                        "to": [12, 2, 16],
                        "faces": {
                            "north": {"uv": [8, 7, 16, 8], "texture": "#2"},
                            "east": {"uv": [0, 11, 16, 12], "texture": "#2"},
                            "south": {"uv": [0, 9, 8, 10], "texture": "#2"},
                            "west": {"uv": [0, 3, 16, 4], "texture": "#2"},
                            "up": {"uv": [0, 0, 8, 16], "texture": "#2"},
                            "down": {"uv": [8, 0, 16, 16], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 2, 15],
                        "to": [12, 5, 16],
                        "faces": {
                            "north": {"uv": [8, 6, 16, 9], "texture": "#2"},
                            "east": {"uv": [13, 6, 14, 9], "texture": "#2"},
                            "south": {"uv": [0, 6, 8, 9], "texture": "#2"},
                            "west": {"uv": [11, 6, 12, 9], "texture": "#2"},
                            "up": {"uv": [0, 5, 8, 6], "texture": "#2"},
                            "down": {"uv": [0, 9, 8, 10], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 2, 0],
                        "to": [12, 5, 1],
                        "faces": {
                            "north": {"uv": [8, 4, 16, 7], "texture": "#2"},
                            "east": {"uv": [12, 4, 13, 7], "texture": "#2"},
                            "south": {"uv": [0, 4, 8, 7], "texture": "#2"},
                            "west": {"uv": [1, 4, 2, 7], "texture": "#2"},
                            "up": {"uv": [3, 3, 11, 4], "texture": "#2"},
                            "down": {"uv": [3, 7, 11, 8], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 5, 0],
                        "to": [6, 6, 1],
                        "faces": {
                            "north": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "east": {"uv": [13, 11, 14, 12], "texture": "#2"},
                            "south": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "west": {"uv": [13, 11, 14, 12], "texture": "#2"},
                            "up": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "down": {"uv": [13, 11, 15, 12], "texture": "#2"}
                        }
                    },
                    {
                        "from": [10, 5, 0],
                        "to": [12, 6, 1],
                        "faces": {
                            "north": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "east": {"uv": [7, 7, 8, 8], "texture": "#2"},
                            "south": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "west": {"uv": [7, 7, 8, 8], "texture": "#2"},
                            "up": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "down": {"uv": [7, 7, 9, 8], "texture": "#2"}
                        }
                    },
                    {
                        "from": [11, 6, 0],
                        "to": [12, 7, 1],
                        "faces": {
                            "north": {"uv": [3, 3, 4, 4], "texture": "#2"},
                            "east": {"uv": [8, 3, 9, 4], "texture": "#2"},
                            "south": {"uv": [7, 3, 8, 4], "texture": "#2"},
                            "west": {"uv": [6, 3, 7, 4], "texture": "#2"},
                            "up": {"uv": [5, 3, 6, 4], "texture": "#2"},
                            "down": {"uv": [4, 3, 5, 4], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 6, 0],
                        "to": [5, 7, 1],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "south": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#2"}
                        }
                    },
                    {
                        "from": [6, 6, 4],
                        "to": [10, 10, 14],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 4, 4], "texture": "#0"},
                            "east": {"uv": [0, 0, 10, 4], "texture": "#0"},
                            "south": {"uv": [0, 0, 4, 4], "texture": "#0"},
                            "west": {"uv": [0, 0, 10, 4], "texture": "#0"},
                            "up": {"uv": [0, 0, 4, 10], "texture": "#0"},
                            "down": {"uv": [0, 0, 4, 10], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6.5, 6.5, 0],
                        "to": [9.5, 9.5, 4],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 3, 3], "texture": "#0"},
                            "east": {"uv": [0, 0, 4, 3], "texture": "#0"},
                            "south": {"uv": [0, 0, 3, 3], "texture": "#0"},
                            "west": {"uv": [0, 0, 4, 3], "texture": "#0"},
                            "up": {"uv": [0, 0, 3, 4], "texture": "#0"},
                            "down": {"uv": [0, 0, 3, 4], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6.75, 6.75, -4],
                        "to": [9.25, 9.25, -3],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [1, 9, 3.5, 11.5], "texture": "#1"},
                            "east": {"uv": [0, 0, 1, 2.5], "texture": "#0"},
                            "south": {"uv": [0, 0, 2.5, 2.5], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 2.5], "texture": "#0"},
                            "up": {"uv": [0, 0, 2.5, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2.5, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 7, -3],
                        "to": [9, 9, 0],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 2], "texture": "#0"},
                            "east": {"uv": [0, 0, 3, 2], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 2], "texture": "#0"},
                            "west": {"uv": [0, 0, 3, 2], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 3], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 3], "texture": "#0"}
                        }
                    },
                    {
                        "from": [9, 6, -5],
                        "to": [10, 10, -4],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "south": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6, 6, -5],
                        "to": [7, 10, -4],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "south": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 9, -5],
                        "to": [9, 10, -4],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 6, -5],
                        "to": [9, 7, -4],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [10, 7, 8],
                        "to": [12, 8, 9],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [4, 7, 8],
                        "to": [6, 8, 9],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 7, 14],
                        "to": [9, 9, 15],
                        "rotation": {"angle": 22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 2], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 2], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 2], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 2], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    }
                ],
                "groups": [
                    {
                        "name": "Left",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [
                            {
                                "name": "Side_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [0, 1, 2, 3, 4]
                            },
                            {
                                "name": "Wheel_Front_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [5, 6, 7, 8]
                            },
                            {
                                "name": "Wheel_Back_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [9, 10, 11, 12]
                            }
                        ]
                    },
                    {
                        "name": "Right",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [
                            {
                                "name": "Side_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [13, 14, 15, 16, 17]
                            },
                            {
                                "name": "Wheel_Front_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [18, 19, 20, 21]
                            },
                            {
                                "name": "Wheel_Back_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [22, 23, 24, 25]
                            }
                        ]
                    },
                    {
                        "name": "Middle",
                        "origin": [8, 8, 8],
                        "color": 0,
                        "children": [26, 27, 28, 29, 30, 31, 32]
                    },
                    {
                        "name": "Cannon",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43]
                    }
                ]
            }
            """;
            //endregion

    private static final String blockModelPattern2 =
            //region Model Variant 2
            """
            {
                "credit": "Made with Blockbench",
                "textures": {
                    "0": "block/anvil",
                    "1": "block/blackstone_top",
                    "2": "#PLANK_NAMESPACE:block/#PLANK_PATH",
            		"3": "#LOG_NAMESPACE:block/#LOG_PATH",
                    "particle": "block/anvil"
                },
                "elements": [
                    {
                        "from": [3, 1, 15],
                        "to": [4, 6, 16],
                        "faces": {
                            "north": {"uv": [8, 3, 9, 8], "texture": "#0"},
                            "east": {"uv": [6, 9, 7, 14], "texture": "#0"},
                            "south": {"uv": [3, 2, 4, 7], "texture": "#0"},
                            "west": {"uv": [2, 7, 3, 12], "texture": "#0"},
                            "up": {"uv": [5, 6, 6, 7], "texture": "#0"},
                            "down": {"uv": [4, 13, 5, 14], "texture": "#0"}
                        }
                    },
                    {
                        "from": [3, 1, 0],
                        "to": [4, 9, 1],
                        "faces": {
                            "north": {"uv": [0, 8, 1, 16], "texture": "#0"},
                            "east": {"uv": [1, 2, 2, 10], "texture": "#0"},
                            "south": {"uv": [5, 5, 6, 13], "texture": "#0"},
                            "west": {"uv": [3, 2, 4, 10], "texture": "#0"},
                            "up": {"uv": [5, 3, 6, 4], "texture": "#0"},
                            "down": {"uv": [3, 14, 4, 15], "texture": "#0"}
                        }
                    },
                    {
                        "from": [3, 8, 1],
                        "to": [4, 9, 6],
                        "faces": {
                            "north": {"uv": [0, 7, 1, 8], "texture": "#2"},
                            "east": {"uv": [1, 7, 6, 8], "texture": "#2"},
                            "south": {"uv": [11, 7, 12, 8], "texture": "#2"},
                            "west": {"uv": [6, 7, 11, 8], "texture": "#2"},
                            "up": {"uv": [11, 7, 16, 8], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 8, 6, 9], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [3, 6, 1],
                        "to": [4, 8, 12],
                        "faces": {
                            "north": {"uv": [12, 9, 13, 11], "texture": "#2"},
                            "east": {"uv": [1, 9, 12, 11], "texture": "#2"},
                            "south": {"uv": [0, 9, 1, 11], "texture": "#2"},
                            "west": {"uv": [1, 9, 12, 11], "texture": "#2"},
                            "up": {"uv": [1, 8, 12, 9], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 11, 12, 12], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [3, 1, 1],
                        "to": [4, 6, 15],
                        "faces": {
                            "north": {"uv": [15, 11, 16, 16], "texture": "#2"},
                            "east": {"uv": [1, 11, 15, 16], "texture": "#2"},
                            "south": {"uv": [0, 11, 1, 16], "texture": "#2"},
                            "west": {"uv": [1, 11, 15, 16], "texture": "#2"},
                            "up": {"uv": [1, 10, 15, 11], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 10, 15, 11], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [1, 2, 3],
                        "to": [3, 3, 4],
                        "faces": {
                            "north": {"uv": [4, 6, 6, 7], "texture": "#1"},
                            "east": {"uv": [8, 7, 9, 8], "texture": "#1"},
                            "south": {"uv": [8, 11, 10, 12], "texture": "#1"},
                            "west": {"uv": [10, 6, 11, 7], "texture": "#1"},
                            "up": {"uv": [7, 4, 9, 5], "texture": "#1"},
                            "down": {"uv": [4, 8, 6, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [1.5, 1, 1],
                        "to": [2.5, 4, 2],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 3], "texture": "#3"},
                            "east": {"uv": [6, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [9, 8, 10, 11], "texture": "#2"},
                            "west": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#3"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 0, 2],
                        "to": [2.5, 5, 5],
                        "faces": {
                            "north": {"uv": [15, 0, 16, 5], "texture": "#3"},
                            "east": {"uv": [1, 12, 6, 15], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [6, 5, 7, 10], "texture": "#3"},
                            "west": {"uv": [6, 12, 11, 15], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [11, 8, 12, 11], "texture": "#3"},
                            "down": {"uv": [9, 0, 10, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 1, 5],
                        "to": [2.5, 4, 6],
                        "faces": {
                            "north": {"uv": [5, 8, 6, 11], "texture": "#2"},
                            "east": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "south": {"uv": [5, 0, 6, 3], "texture": "#3"},
                            "west": {"uv": [5, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [7, 11, 8, 12], "texture": "#3"},
                            "down": {"uv": [9, 1, 10, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1, 2, 12],
                        "to": [3, 3, 13],
                        "faces": {
                            "north": {"uv": [4, 6, 6, 7], "texture": "#1"},
                            "east": {"uv": [8, 7, 9, 8], "texture": "#1"},
                            "south": {"uv": [8, 11, 10, 12], "texture": "#1"},
                            "west": {"uv": [10, 6, 11, 7], "texture": "#1"},
                            "up": {"uv": [7, 4, 9, 5], "texture": "#1"},
                            "down": {"uv": [4, 8, 6, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [1.5, 1, 10],
                        "to": [2.5, 4, 11],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 3], "texture": "#3"},
                            "east": {"uv": [6, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [9, 8, 10, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 8, 7], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#3"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 0, 11],
                        "to": [2.5, 5, 14],
                        "faces": {
                            "north": {"uv": [15, 0, 16, 5], "texture": "#3"},
                            "east": {"uv": [2, 11, 7, 14], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [6, 5, 7, 10], "texture": "#3"},
                            "west": {"uv": [6, 12, 11, 15], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [11, 8, 12, 11], "texture": "#3"},
                            "down": {"uv": [9, 0, 10, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 1, 14],
                        "to": [2.5, 4, 15],
                        "faces": {
                            "north": {"uv": [5, 8, 6, 11], "texture": "#2"},
                            "east": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "south": {"uv": [5, 0, 6, 3], "texture": "#3"},
                            "west": {"uv": [5, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [7, 11, 8, 12], "texture": "#3"},
                            "down": {"uv": [9, 1, 10, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [12, 1, 15],
                        "to": [13, 6, 16],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [9, 3, 8, 8], "texture": "#0"},
                            "east": {"uv": [3, 7, 2, 12], "texture": "#0"},
                            "south": {"uv": [4, 2, 3, 7], "texture": "#0"},
                            "west": {"uv": [7, 9, 6, 14], "texture": "#0"},
                            "up": {"uv": [6, 6, 5, 7], "texture": "#0"},
                            "down": {"uv": [5, 13, 4, 14], "texture": "#0"}
                        }
                    },
                    {
                        "from": [12, 1, 0],
                        "to": [13, 9, 1],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 8, 0, 16], "texture": "#0"},
                            "east": {"uv": [4, 2, 3, 10], "texture": "#0"},
                            "south": {"uv": [6, 5, 5, 13], "texture": "#0"},
                            "west": {"uv": [2, 2, 1, 10], "texture": "#0"},
                            "up": {"uv": [6, 3, 5, 4], "texture": "#0"},
                            "down": {"uv": [4, 14, 3, 15], "texture": "#0"}
                        }
                    },
                    {
                        "from": [12, 8, 1],
                        "to": [13, 9, 6],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 7, 0, 8], "texture": "#2"},
                            "east": {"uv": [11, 7, 6, 8], "texture": "#2"},
                            "south": {"uv": [12, 7, 11, 8], "texture": "#2"},
                            "west": {"uv": [6, 7, 1, 8], "texture": "#2"},
                            "up": {"uv": [11, 8, 16, 7], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 9, 6, 8], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [12, 6, 1],
                        "to": [13, 8, 12],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [13, 9, 12, 11], "texture": "#2"},
                            "east": {"uv": [12, 9, 1, 11], "texture": "#2"},
                            "south": {"uv": [1, 9, 0, 11], "texture": "#2"},
                            "west": {"uv": [12, 9, 1, 11], "texture": "#2"},
                            "up": {"uv": [1, 9, 12, 8], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 12, 12, 11], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [12, 1, 1],
                        "to": [13, 6, 15],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 11, 15, 16], "texture": "#2"},
                            "east": {"uv": [15, 11, 1, 16], "texture": "#2"},
                            "south": {"uv": [1, 11, 0, 16], "texture": "#2"},
                            "west": {"uv": [15, 11, 1, 16], "texture": "#2"},
                            "up": {"uv": [1, 11, 15, 10], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 11, 15, 10], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [13, 2, 3],
                        "to": [15, 3, 4],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 6, 4, 7], "texture": "#1"},
                            "east": {"uv": [11, 6, 10, 7], "texture": "#1"},
                            "south": {"uv": [10, 11, 8, 12], "texture": "#1"},
                            "west": {"uv": [9, 7, 8, 8], "texture": "#1"},
                            "up": {"uv": [9, 4, 7, 5], "texture": "#1"},
                            "down": {"uv": [6, 8, 4, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [13.5, 1, 1],
                        "to": [14.5, 4, 2],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 0, 0, 3], "texture": "#3"},
                            "east": {"uv": [8, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [10, 8, 9, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [1, 0, 0, 1], "texture": "#3"},
                            "down": {"uv": [1, 0, 0, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 0, 2],
                        "to": [14.5, 5, 5],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 0, 15, 5], "texture": "#3"},
                            "east": {"uv": [7, 15, 12, 12], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [7, 5, 6, 10], "texture": "#3"},
                            "west": {"uv": [2, 15, 7, 12], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [12, 8, 11, 11], "texture": "#3"},
                            "down": {"uv": [10, 0, 9, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 1, 5],
                        "to": [14.5, 4, 6],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 8, 5, 11], "texture": "#2"},
                            "east": {"uv": [6, 4, 5, 7], "texture": "#2"},
                            "south": {"uv": [6, 0, 5, 3], "texture": "#3"},
                            "west": {"uv": [10, 4, 9, 7], "texture": "#2"},
                            "up": {"uv": [8, 11, 7, 12], "texture": "#3"},
                            "down": {"uv": [10, 1, 9, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13, 2, 12],
                        "to": [15, 3, 13],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 6, 4, 7], "texture": "#1"},
                            "east": {"uv": [11, 6, 10, 7], "texture": "#1"},
                            "south": {"uv": [10, 11, 8, 12], "texture": "#1"},
                            "west": {"uv": [9, 7, 8, 8], "texture": "#1"},
                            "up": {"uv": [9, 4, 7, 5], "texture": "#1"},
                            "down": {"uv": [6, 8, 4, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [13.5, 1, 10],
                        "to": [14.5, 4, 11],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 0, 0, 3], "texture": "#3"},
                            "east": {"uv": [8, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [10, 8, 9, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [1, 0, 0, 1], "texture": "#3"},
                            "down": {"uv": [1, 0, 0, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 0, 11],
                        "to": [14.5, 5, 14],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 0, 15, 5], "texture": "#3"},
                            "east": {"uv": [6, 15, 11, 12], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [7, 5, 6, 10], "texture": "#3"},
                            "west": {"uv": [1, 15, 6, 12], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [12, 8, 11, 11], "texture": "#3"},
                            "down": {"uv": [10, 0, 9, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 1, 14],
                        "to": [14.5, 4, 15],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 8, 5, 11], "texture": "#2"},
                            "east": {"uv": [6, 4, 5, 7], "texture": "#2"},
                            "south": {"uv": [6, 0, 5, 3], "texture": "#3"},
                            "west": {"uv": [10, 4, 9, 7], "texture": "#2"},
                            "up": {"uv": [8, 11, 7, 12], "texture": "#3"},
                            "down": {"uv": [10, 1, 9, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [4, 1, 0],
                        "to": [12, 2, 16],
                        "faces": {
                            "north": {"uv": [8, 7, 16, 8], "texture": "#2"},
                            "east": {"uv": [0, 11, 16, 12], "texture": "#2"},
                            "south": {"uv": [0, 9, 8, 10], "texture": "#2"},
                            "west": {"uv": [0, 3, 16, 4], "texture": "#2"},
                            "up": {"uv": [0, 0, 8, 16], "texture": "#2"},
                            "down": {"uv": [8, 0, 16, 16], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 2, 15],
                        "to": [12, 5, 16],
                        "faces": {
                            "north": {"uv": [8, 6, 16, 9], "texture": "#2"},
                            "east": {"uv": [13, 6, 14, 9], "texture": "#2"},
                            "south": {"uv": [0, 6, 8, 9], "texture": "#2"},
                            "west": {"uv": [11, 6, 12, 9], "texture": "#2"},
                            "up": {"uv": [0, 5, 8, 6], "texture": "#2"},
                            "down": {"uv": [0, 9, 8, 10], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 2, 0],
                        "to": [12, 5, 1],
                        "faces": {
                            "north": {"uv": [8, 4, 16, 7], "texture": "#2"},
                            "east": {"uv": [12, 4, 13, 7], "texture": "#2"},
                            "south": {"uv": [0, 4, 8, 7], "texture": "#2"},
                            "west": {"uv": [1, 4, 2, 7], "texture": "#2"},
                            "up": {"uv": [3, 3, 11, 4], "texture": "#2"},
                            "down": {"uv": [3, 7, 11, 8], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 5, 0],
                        "to": [6, 6, 1],
                        "faces": {
                            "north": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "east": {"uv": [13, 11, 14, 12], "texture": "#2"},
                            "south": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "west": {"uv": [13, 11, 14, 12], "texture": "#2"},
                            "up": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "down": {"uv": [13, 11, 15, 12], "texture": "#2"}
                        }
                    },
                    {
                        "from": [10, 5, 0],
                        "to": [12, 6, 1],
                        "faces": {
                            "north": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "east": {"uv": [7, 7, 8, 8], "texture": "#2"},
                            "south": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "west": {"uv": [7, 7, 8, 8], "texture": "#2"},
                            "up": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "down": {"uv": [7, 7, 9, 8], "texture": "#2"}
                        }
                    },
                    {
                        "from": [11, 6, 0],
                        "to": [12, 7, 1],
                        "faces": {
                            "north": {"uv": [3, 3, 4, 4], "texture": "#2"},
                            "east": {"uv": [8, 3, 9, 4], "texture": "#2"},
                            "south": {"uv": [7, 3, 8, 4], "texture": "#2"},
                            "west": {"uv": [6, 3, 7, 4], "texture": "#2"},
                            "up": {"uv": [5, 3, 6, 4], "texture": "#2"},
                            "down": {"uv": [4, 3, 5, 4], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 6, 0],
                        "to": [5, 7, 1],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "south": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#2"}
                        }
                    },
                    {
                        "from": [6, 6, 4],
                        "to": [10, 10, 14],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 4, 4], "texture": "#0"},
                            "east": {"uv": [0, 0, 10, 4], "texture": "#0"},
                            "south": {"uv": [0, 0, 4, 4], "texture": "#0"},
                            "west": {"uv": [0, 0, 10, 4], "texture": "#0"},
                            "up": {"uv": [0, 0, 4, 10], "texture": "#0"},
                            "down": {"uv": [0, 0, 4, 10], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6.5, 6.5, 0],
                        "to": [9.5, 9.5, 4],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 3, 3], "texture": "#0"},
                            "east": {"uv": [0, 0, 4, 3], "texture": "#0"},
                            "south": {"uv": [0, 0, 3, 3], "texture": "#0"},
                            "west": {"uv": [0, 0, 4, 3], "texture": "#0"},
                            "up": {"uv": [0, 0, 3, 4], "texture": "#0"},
                            "down": {"uv": [0, 0, 3, 4], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6.75, 6.75, -4],
                        "to": [9.25, 9.25, -3],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [1, 9, 3.5, 11.5], "texture": "#1"},
                            "east": {"uv": [0, 0, 1, 2.5], "texture": "#0"},
                            "south": {"uv": [0, 0, 2.5, 2.5], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 2.5], "texture": "#0"},
                            "up": {"uv": [0, 0, 2.5, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2.5, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 7, -3],
                        "to": [9, 9, 0],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 2], "texture": "#0"},
                            "east": {"uv": [0, 0, 3, 2], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 2], "texture": "#0"},
                            "west": {"uv": [0, 0, 3, 2], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 3], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 3], "texture": "#0"}
                        }
                    },
                    {
                        "from": [9, 6, -5],
                        "to": [10, 10, -4],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "south": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6, 6, -5],
                        "to": [7, 10, -4],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "south": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 4], "texture": "#0"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 9, -5],
                        "to": [9, 10, -4],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 6, -5],
                        "to": [9, 7, -4],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [10, 7, 8],
                        "to": [12, 8, 9],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [4, 7, 8],
                        "to": [6, 8, 9],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 7, 14],
                        "to": [9, 9, 15],
                        "rotation": {"angle": 45, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 2], "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 2], "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 2], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 2], "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    }
                ],
                "groups": [
                    {
                        "name": "Left",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [
                            {
                                "name": "Side_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [0, 1, 2, 3, 4]
                            },
                            {
                                "name": "Wheel_Front_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [5, 6, 7, 8]
                            },
                            {
                                "name": "Wheel_Back_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [9, 10, 11, 12]
                            }
                        ]
                    },
                    {
                        "name": "Right",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [
                            {
                                "name": "Side_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [13, 14, 15, 16, 17]
                            },
                            {
                                "name": "Wheel_Front_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [18, 19, 20, 21]
                            },
                            {
                                "name": "Wheel_Back_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [22, 23, 24, 25]
                            }
                        ]
                    },
                    {
                        "name": "Middle",
                        "origin": [8, 8, 8],
                        "color": 0,
                        "children": [26, 27, 28, 29, 30, 31, 32]
                    },
                    {
                        "name": "Cannon",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43]
                    }
                ]
            }
            """;
            //endregion

    private static final String blockModelPattern3 =
            //region Model Variant 3
            """
            {
                "credit": "Made with Blockbench",
                "textures": {
                    "0": "block/anvil",
                    "1": "block/blackstone_top",
                    "2": "#PLANK_NAMESPACE:block/#PLANK_PATH",
            		"3": "#LOG_NAMESPACE:block/#LOG_PATH",
                    "particle": "block/anvil"
                },
                "elements": [
                    {
                        "from": [3, 1, 15],
                        "to": [4, 6, 16],
                        "faces": {
                            "north": {"uv": [8, 3, 9, 8], "texture": "#0"},
                            "east": {"uv": [6, 9, 7, 14], "texture": "#0"},
                            "south": {"uv": [3, 2, 4, 7], "texture": "#0"},
                            "west": {"uv": [2, 7, 3, 12], "texture": "#0"},
                            "up": {"uv": [5, 6, 6, 7], "texture": "#0"},
                            "down": {"uv": [4, 13, 5, 14], "texture": "#0"}
                        }
                    },
                    {
                        "from": [3, 1, 0],
                        "to": [4, 9, 1],
                        "faces": {
                            "north": {"uv": [0, 8, 1, 16], "texture": "#0"},
                            "east": {"uv": [1, 2, 2, 10], "texture": "#0"},
                            "south": {"uv": [5, 5, 6, 13], "texture": "#0"},
                            "west": {"uv": [3, 2, 4, 10], "texture": "#0"},
                            "up": {"uv": [5, 3, 6, 4], "texture": "#0"},
                            "down": {"uv": [3, 14, 4, 15], "texture": "#0"}
                        }
                    },
                    {
                        "from": [3, 8, 1],
                        "to": [4, 9, 6],
                        "faces": {
                            "north": {"uv": [0, 7, 1, 8], "texture": "#2"},
                            "east": {"uv": [1, 7, 6, 8], "texture": "#2"},
                            "south": {"uv": [11, 7, 12, 8], "texture": "#2"},
                            "west": {"uv": [6, 7, 11, 8], "texture": "#2"},
                            "up": {"uv": [11, 7, 16, 8], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 8, 6, 9], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [3, 6, 1],
                        "to": [4, 8, 12],
                        "faces": {
                            "north": {"uv": [12, 9, 13, 11], "texture": "#2"},
                            "east": {"uv": [1, 9, 12, 11], "texture": "#2"},
                            "south": {"uv": [0, 9, 1, 11], "texture": "#2"},
                            "west": {"uv": [1, 9, 12, 11], "texture": "#2"},
                            "up": {"uv": [1, 8, 12, 9], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 11, 12, 12], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [3, 1, 1],
                        "to": [4, 6, 15],
                        "faces": {
                            "north": {"uv": [15, 11, 16, 16], "texture": "#2"},
                            "east": {"uv": [1, 11, 15, 16], "texture": "#2"},
                            "south": {"uv": [0, 11, 1, 16], "texture": "#2"},
                            "west": {"uv": [1, 11, 15, 16], "texture": "#2"},
                            "up": {"uv": [1, 10, 15, 11], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 10, 15, 11], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [1, 2, 3],
                        "to": [3, 3, 4],
                        "faces": {
                            "north": {"uv": [4, 6, 6, 7], "texture": "#1"},
                            "east": {"uv": [8, 7, 9, 8], "texture": "#1"},
                            "south": {"uv": [8, 11, 10, 12], "texture": "#1"},
                            "west": {"uv": [10, 6, 11, 7], "texture": "#1"},
                            "up": {"uv": [7, 4, 9, 5], "texture": "#1"},
                            "down": {"uv": [4, 8, 6, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [1.5, 1, 1],
                        "to": [2.5, 4, 2],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 3], "texture": "#3"},
                            "east": {"uv": [6, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [9, 8, 10, 11], "texture": "#2"},
                            "west": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#3"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 0, 2],
                        "to": [2.5, 5, 5],
                        "faces": {
                            "north": {"uv": [15, 0, 16, 5], "texture": "#3"},
                            "east": {"uv": [1, 12, 6, 15], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [6, 5, 7, 10], "texture": "#3"},
                            "west": {"uv": [6, 12, 11, 15], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [11, 8, 12, 11], "texture": "#3"},
                            "down": {"uv": [9, 0, 10, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 1, 5],
                        "to": [2.5, 4, 6],
                        "faces": {
                            "north": {"uv": [5, 8, 6, 11], "texture": "#2"},
                            "east": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "south": {"uv": [5, 0, 6, 3], "texture": "#3"},
                            "west": {"uv": [5, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [7, 11, 8, 12], "texture": "#3"},
                            "down": {"uv": [9, 1, 10, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1, 2, 12],
                        "to": [3, 3, 13],
                        "faces": {
                            "north": {"uv": [4, 6, 6, 7], "texture": "#1"},
                            "east": {"uv": [8, 7, 9, 8], "texture": "#1"},
                            "south": {"uv": [8, 11, 10, 12], "texture": "#1"},
                            "west": {"uv": [10, 6, 11, 7], "texture": "#1"},
                            "up": {"uv": [7, 4, 9, 5], "texture": "#1"},
                            "down": {"uv": [4, 8, 6, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [1.5, 1, 10],
                        "to": [2.5, 4, 11],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 3], "texture": "#3"},
                            "east": {"uv": [6, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [9, 8, 10, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 8, 7], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#3"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 0, 11],
                        "to": [2.5, 5, 14],
                        "faces": {
                            "north": {"uv": [15, 0, 16, 5], "texture": "#3"},
                            "east": {"uv": [2, 11, 7, 14], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [6, 5, 7, 10], "texture": "#3"},
                            "west": {"uv": [6, 12, 11, 15], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [11, 8, 12, 11], "texture": "#3"},
                            "down": {"uv": [9, 0, 10, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [1.5, 1, 14],
                        "to": [2.5, 4, 15],
                        "faces": {
                            "north": {"uv": [5, 8, 6, 11], "texture": "#2"},
                            "east": {"uv": [9, 4, 10, 7], "texture": "#2"},
                            "south": {"uv": [5, 0, 6, 3], "texture": "#3"},
                            "west": {"uv": [5, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [7, 11, 8, 12], "texture": "#3"},
                            "down": {"uv": [9, 1, 10, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [12, 1, 15],
                        "to": [13, 6, 16],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [9, 3, 8, 8], "texture": "#0"},
                            "east": {"uv": [3, 7, 2, 12], "texture": "#0"},
                            "south": {"uv": [4, 2, 3, 7], "texture": "#0"},
                            "west": {"uv": [7, 9, 6, 14], "texture": "#0"},
                            "up": {"uv": [6, 6, 5, 7], "texture": "#0"},
                            "down": {"uv": [5, 13, 4, 14], "texture": "#0"}
                        }
                    },
                    {
                        "from": [12, 1, 0],
                        "to": [13, 9, 1],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 8, 0, 16], "texture": "#0"},
                            "east": {"uv": [4, 2, 3, 10], "texture": "#0"},
                            "south": {"uv": [6, 5, 5, 13], "texture": "#0"},
                            "west": {"uv": [2, 2, 1, 10], "texture": "#0"},
                            "up": {"uv": [6, 3, 5, 4], "texture": "#0"},
                            "down": {"uv": [4, 14, 3, 15], "texture": "#0"}
                        }
                    },
                    {
                        "from": [12, 8, 1],
                        "to": [13, 9, 6],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 7, 0, 8], "texture": "#2"},
                            "east": {"uv": [11, 7, 6, 8], "texture": "#2"},
                            "south": {"uv": [12, 7, 11, 8], "texture": "#2"},
                            "west": {"uv": [6, 7, 1, 8], "texture": "#2"},
                            "up": {"uv": [11, 8, 16, 7], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 9, 6, 8], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [12, 6, 1],
                        "to": [13, 8, 12],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [13, 9, 12, 11], "texture": "#2"},
                            "east": {"uv": [12, 9, 1, 11], "texture": "#2"},
                            "south": {"uv": [1, 9, 0, 11], "texture": "#2"},
                            "west": {"uv": [12, 9, 1, 11], "texture": "#2"},
                            "up": {"uv": [1, 9, 12, 8], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 12, 12, 11], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [12, 1, 1],
                        "to": [13, 6, 15],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 11, 15, 16], "texture": "#2"},
                            "east": {"uv": [15, 11, 1, 16], "texture": "#2"},
                            "south": {"uv": [1, 11, 0, 16], "texture": "#2"},
                            "west": {"uv": [15, 11, 1, 16], "texture": "#2"},
                            "up": {"uv": [1, 11, 15, 10], "rotation": 90, "texture": "#2"},
                            "down": {"uv": [1, 11, 15, 10], "rotation": 90, "texture": "#2"}
                        }
                    },
                    {
                        "from": [13, 2, 3],
                        "to": [15, 3, 4],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 6, 4, 7], "texture": "#1"},
                            "east": {"uv": [11, 6, 10, 7], "texture": "#1"},
                            "south": {"uv": [10, 11, 8, 12], "texture": "#1"},
                            "west": {"uv": [9, 7, 8, 8], "texture": "#1"},
                            "up": {"uv": [9, 4, 7, 5], "texture": "#1"},
                            "down": {"uv": [6, 8, 4, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [13.5, 1, 1],
                        "to": [14.5, 4, 2],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 0, 0, 3], "texture": "#3"},
                            "east": {"uv": [8, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [10, 8, 9, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [1, 0, 0, 1], "texture": "#3"},
                            "down": {"uv": [1, 0, 0, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 0, 2],
                        "to": [14.5, 5, 5],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 0, 15, 5], "texture": "#3"},
                            "east": {"uv": [7, 15, 12, 12], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [7, 5, 6, 10], "texture": "#3"},
                            "west": {"uv": [2, 15, 7, 12], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [12, 8, 11, 11], "texture": "#3"},
                            "down": {"uv": [10, 0, 9, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 1, 5],
                        "to": [14.5, 4, 6],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 8, 5, 11], "texture": "#2"},
                            "east": {"uv": [6, 4, 5, 7], "texture": "#2"},
                            "south": {"uv": [6, 0, 5, 3], "texture": "#3"},
                            "west": {"uv": [10, 4, 9, 7], "texture": "#2"},
                            "up": {"uv": [8, 11, 7, 12], "texture": "#3"},
                            "down": {"uv": [10, 1, 9, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13, 2, 12],
                        "to": [15, 3, 13],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 6, 4, 7], "texture": "#1"},
                            "east": {"uv": [11, 6, 10, 7], "texture": "#1"},
                            "south": {"uv": [10, 11, 8, 12], "texture": "#1"},
                            "west": {"uv": [9, 7, 8, 8], "texture": "#1"},
                            "up": {"uv": [9, 4, 7, 5], "texture": "#1"},
                            "down": {"uv": [6, 8, 4, 9], "texture": "#1"}
                        }
                    },
                    {
                        "from": [13.5, 1, 10],
                        "to": [14.5, 4, 11],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [1, 0, 0, 3], "texture": "#3"},
                            "east": {"uv": [8, 4, 7, 7], "texture": "#2"},
                            "south": {"uv": [10, 8, 9, 11], "texture": "#2"},
                            "west": {"uv": [7, 4, 6, 7], "texture": "#2"},
                            "up": {"uv": [1, 0, 0, 1], "texture": "#3"},
                            "down": {"uv": [1, 0, 0, 1], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 0, 11],
                        "to": [14.5, 5, 14],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [16, 0, 15, 5], "texture": "#3"},
                            "east": {"uv": [6, 15, 11, 12], "rotation": 90, "texture": "#2"},
                            "south": {"uv": [7, 5, 6, 10], "texture": "#3"},
                            "west": {"uv": [1, 15, 6, 12], "rotation": 90, "texture": "#2"},
                            "up": {"uv": [12, 8, 11, 11], "texture": "#3"},
                            "down": {"uv": [10, 0, 9, 3], "texture": "#3"}
                        }
                    },
                    {
                        "from": [13.5, 1, 14],
                        "to": [14.5, 4, 15],
                        "rotation": {"angle": 0, "axis": "y", "origin": [16, 0, 0]},
                        "faces": {
                            "north": {"uv": [6, 8, 5, 11], "texture": "#2"},
                            "east": {"uv": [6, 4, 5, 7], "texture": "#2"},
                            "south": {"uv": [6, 0, 5, 3], "texture": "#3"},
                            "west": {"uv": [10, 4, 9, 7], "texture": "#2"},
                            "up": {"uv": [8, 11, 7, 12], "texture": "#3"},
                            "down": {"uv": [10, 1, 9, 2], "texture": "#3"}
                        }
                    },
                    {
                        "from": [4, 1, 0],
                        "to": [12, 2, 16],
                        "faces": {
                            "north": {"uv": [8, 7, 16, 8], "texture": "#2"},
                            "east": {"uv": [0, 11, 16, 12], "texture": "#2"},
                            "south": {"uv": [0, 9, 8, 10], "texture": "#2"},
                            "west": {"uv": [0, 3, 16, 4], "texture": "#2"},
                            "up": {"uv": [0, 0, 8, 16], "texture": "#2"},
                            "down": {"uv": [8, 0, 16, 16], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 2, 15],
                        "to": [12, 5, 16],
                        "faces": {
                            "north": {"uv": [8, 6, 16, 9], "texture": "#2"},
                            "east": {"uv": [13, 6, 14, 9], "texture": "#2"},
                            "south": {"uv": [0, 6, 8, 9], "texture": "#2"},
                            "west": {"uv": [11, 6, 12, 9], "texture": "#2"},
                            "up": {"uv": [0, 5, 8, 6], "texture": "#2"},
                            "down": {"uv": [0, 9, 8, 10], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 2, 0],
                        "to": [12, 5, 1],
                        "faces": {
                            "north": {"uv": [8, 4, 16, 7], "texture": "#2"},
                            "east": {"uv": [12, 4, 13, 7], "texture": "#2"},
                            "south": {"uv": [0, 4, 8, 7], "texture": "#2"},
                            "west": {"uv": [1, 4, 2, 7], "texture": "#2"},
                            "up": {"uv": [3, 3, 11, 4], "texture": "#2"},
                            "down": {"uv": [3, 7, 11, 8], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 5, 0],
                        "to": [6, 6, 1],
                        "faces": {
                            "north": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "east": {"uv": [13, 11, 14, 12], "texture": "#2"},
                            "south": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "west": {"uv": [13, 11, 14, 12], "texture": "#2"},
                            "up": {"uv": [13, 11, 15, 12], "texture": "#2"},
                            "down": {"uv": [13, 11, 15, 12], "texture": "#2"}
                        }
                    },
                    {
                        "from": [10, 5, 0],
                        "to": [12, 6, 1],
                        "faces": {
                            "north": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "east": {"uv": [7, 7, 8, 8], "texture": "#2"},
                            "south": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "west": {"uv": [7, 7, 8, 8], "texture": "#2"},
                            "up": {"uv": [7, 7, 9, 8], "texture": "#2"},
                            "down": {"uv": [7, 7, 9, 8], "texture": "#2"}
                        }
                    },
                    {
                        "from": [11, 6, 0],
                        "to": [12, 7, 1],
                        "faces": {
                            "north": {"uv": [3, 3, 4, 4], "texture": "#2"},
                            "east": {"uv": [8, 3, 9, 4], "texture": "#2"},
                            "south": {"uv": [7, 3, 8, 4], "texture": "#2"},
                            "west": {"uv": [6, 3, 7, 4], "texture": "#2"},
                            "up": {"uv": [5, 3, 6, 4], "texture": "#2"},
                            "down": {"uv": [4, 3, 5, 4], "texture": "#2"}
                        }
                    },
                    {
                        "from": [4, 6, 0],
                        "to": [5, 7, 1],
                        "faces": {
                            "north": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "east": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "south": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "west": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "up": {"uv": [0, 0, 1, 1], "texture": "#2"},
                            "down": {"uv": [0, 0, 1, 1], "texture": "#2"}
                        }
                    },
                    {
                        "from": [6, 2, 7],
                        "to": [10, 12, 11],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 4, 10], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 10, 4], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 4, 10], "texture": "#0"},
                            "west": {"uv": [0, 0, 10, 4], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 4, 4], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 4, 4], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6.5, 12, 7.5],
                        "to": [9.5, 16, 10.5],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 3, 4], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 4, 3], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 3, 4], "texture": "#0"},
                            "west": {"uv": [0, 0, 4, 3], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 3, 3], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 3, 3], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6.75, 19, 7.75],
                        "to": [9.25, 20, 10.25],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2.5, 1], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 2.5], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 2.5, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 2.5], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [1, 9, 3.5, 11.5], "rotation": 180, "texture": "#1"},
                            "down": {"uv": [0, 0, 2.5, 2.5], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 16, 8],
                        "to": [9, 19, 10],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 3], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 3, 2], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 3], "texture": "#0"},
                            "west": {"uv": [0, 0, 3, 2], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 2], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 2], "texture": "#0"}
                        }
                    },
                    {
                        "from": [9, 20, 7],
                        "to": [10, 21, 11],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 1, 1], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 4], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 4], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 1, 4], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 1, 4], "texture": "#0"}
                        }
                    },
                    {
                        "from": [6, 20, 7],
                        "to": [7, 21, 11],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 1, 1], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 4], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 1, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 4], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 1, 4], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 1, 4], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 20, 10],
                        "to": [9, 21, 11],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 20, 7],
                        "to": [9, 21, 8],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [10, 7, 8],
                        "to": [12, 8, 9],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [4, 7, 8],
                        "to": [6, 8, 9],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 1], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 1], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 1], "texture": "#0"}
                        }
                    },
                    {
                        "from": [7, 1, 8],
                        "to": [9, 2, 10],
                        "rotation": {"angle": -22.5, "axis": "x", "origin": [8, 7.5, 8.5]},
                        "faces": {
                            "north": {"uv": [0, 0, 2, 1], "rotation": 180, "texture": "#0"},
                            "east": {"uv": [0, 0, 1, 2], "rotation": 270, "texture": "#0"},
                            "south": {"uv": [0, 0, 2, 1], "texture": "#0"},
                            "west": {"uv": [0, 0, 1, 2], "rotation": 90, "texture": "#0"},
                            "up": {"uv": [0, 0, 2, 2], "rotation": 180, "texture": "#0"},
                            "down": {"uv": [0, 0, 2, 2], "texture": "#0"}
                        }
                    }
                ],
                "groups": [
                    {
                        "name": "Left",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [
                            {
                                "name": "Side_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [0, 1, 2, 3, 4]
                            },
                            {
                                "name": "Wheel_Front_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [5, 6, 7, 8]
                            },
                            {
                                "name": "Wheel_Back_Left",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [9, 10, 11, 12]
                            }
                        ]
                    },
                    {
                        "name": "Right",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [
                            {
                                "name": "Side_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [13, 14, 15, 16, 17]
                            },
                            {
                                "name": "Wheel_Front_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [18, 19, 20, 21]
                            },
                            {
                                "name": "Wheel_Back_Right",
                                "origin": [0, 0, 0],
                                "color": 0,
                                "children": [22, 23, 24, 25]
                            }
                        ]
                    },
                    {
                        "name": "Middle",
                        "origin": [8, 8, 8],
                        "color": 0,
                        "children": [26, 27, 28, 29, 30, 31, 32]
                    },
                    {
                        "name": "Cannon",
                        "origin": [0, 0, 0],
                        "color": 0,
                        "children": [33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43]
                    }
                ]
            }
            """;
            //endregion

    private static final String itemModelPattern =
            """
            {
                "parent": "#NAMESPACE:block/#PATH_#VARIANT"
            }
            """;

    public static String createCannonBlockStates(Identifier identifier) {
        return blockStatePattern
                .replaceAll("#NAMESPACE", identifier.getNamespace())
                .replaceAll("#PATH", identifier.getPath());
    }

    public static String createCannonBlockModel(Identifier plankTexture, Identifier logTexture, int variant) {
        String base = switch (variant) {
            case 0 -> blockModelPattern0;
            case 1 -> blockModelPattern1;
            case 2 -> blockModelPattern2;
            case 3 -> blockModelPattern3;
            default -> "";
        };

        return base
                .replaceAll("#PLANK_NAMESPACE", plankTexture.getNamespace())
                .replaceAll("#PLANK_PATH", plankTexture.getPath())
                .replaceAll("#LOG_NAMESPACE", logTexture.getNamespace())
                .replaceAll("#LOG_PATH", logTexture.getPath());
    }

    public static String createCannonItemModel(Identifier cannonBlock, int variant) {
        return itemModelPattern
                .replaceAll("#NAMESPACE", cannonBlock.getNamespace())
                .replaceAll("#PATH", cannonBlock.getPath())
                .replaceAll("#VARIANT", String.valueOf(variant));
    }
}
