package com.lahusa.superior_ballistics.util;

import com.lahusa.superior_ballistics.block.entity.CannonBlockEntity;

/**
 * Looks up the ideal range of a cannon shot by its powder amount and angle from a pre-simulated data set
 */
public class CannonDistanceLookup {
    // Range lookup table by powder [0-MAX_POWDER] and barrel angle [0-MAX_ANGLE]
    private static final double[][] lookupTable = {
            // Powder 0
            {
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0
            },
            // Powder 1
            {
                    5.8519850599,
                    9.515749854732553,
                    13.725270588323326,
                    18.207907766483185,
                    21.245243095129926,
                    23.608735069618792,
                    25.242318388849828,
                    26.105951260770894,
                    25.725570989832423,
                    24.670461156736973,
                    22.613572621951565,
                    20.01763851695357,
                    16.68862585542803,
                    12.984913246250521,
                    8.834535645885111,
                    4.492271822408885
            },
            // Powder 2
            {
                    11.7039701198,
                    27.85355761727336,
                    46.6181535097893,
                    60.75526248548138,
                    70.71556113473986,
                    77.91489711946979,
                    82.33446761914684,
                    82.57594882715028,
                    80.62586891324129,
                    75.52680776204248,
                    68.77435936456193,
                    60.11515001790143,
                    50.16121184136679,
                    38.64403985416242,
                    26.230956747114725,
                    13.306785905987565
            },
            // Powder 3
            {
                    17.5559551797,
                    56.80677583726891,
                    93.40373416363629,
                    120.23962568692225,
                    140.01246690340065,
                    150.83790060356858,
                    155.34264112162387,
                    153.576831239585,
                    147.98236621922734,
                    137.74232443087814,
                    124.60984818662182,
                    108.15491560645717,
                    89.12444484772358,
                    68.56009954466938,
                    46.446662740000846,
                    23.456481054541623
            },
            // Powder 4
            {
                    23.4079402396,
                    94.60516034565353,
                    152.5641949074964,
                    193.36370507244393,
                    219.98237872034622,
                    233.61939042734951,
                    236.94103204708466,
                    232.72602545830975,
                    221.45314517090839,
                    204.93813347679622,
                    183.56266538800136,
                    158.58866494677378,
                    130.39631940478597,
                    99.91882271506532,
                    67.58087364178893,
                    34.058467439630796
            },
            // Powder 5
            {
                    29.259925299499997,
                    140.455131382046,
                    222.0710497357586,
                    276.8743118639777,
                    307.4777991426199,
                    321.56866273591703,
                    323.6695903116716,
                    314.3558025594651,
                    297.35865684560764,
                    273.3978718345482,
                    243.7862339147531,
                    209.869856365762,
                    171.9938073126988,
                    131.4630079888592,
                    88.80232296581529,
                    44.703411868259145
            }
    };

    public static double getShotRangeEstimate(int powder, int angle) {
        if(powder < 0 || powder > CannonBlockEntity.MAX_POWDER || angle < 0 || angle > CannonBlockEntity.MAX_ANGLE) {
            throw new IllegalStateException("Wrong parameters supplied at cannon range lookup with powder " + powder + " and angle " + angle);
        }

        return lookupTable[powder][angle];
    }

}
