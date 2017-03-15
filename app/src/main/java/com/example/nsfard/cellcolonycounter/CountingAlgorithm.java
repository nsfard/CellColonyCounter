package com.example.nsfard.cellcolonycounter;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by nsfard on 1/31/17.
 */
public class CountingAlgorithm {

    public static class ML_Prediction {
        public List<MatOfPoint> contours;
        public int count;
    }

    private static CountingAlgorithm instance = null;
    private static String inputImage = null;
    private static String outputImage = null;
    private static int outputCount = 0;
    private static AlgorithmCompletedListener listener = null;
    private static double avg_area;
    private static double avg_perim;
    static ML_Prediction results = new ML_Prediction();


    public static void setListener(AlgorithmCompletedListener listener) {
        instance = new CountingAlgorithm();
        instance.listener = listener;
    }

    public static int predict(double[] atts) {
        if (atts.length != 4) { return -1; }
        int[] classes = new int[7];

        if (atts[1] <= 0.37844383716583252) {
            if (atts[1] <= 0.3129761815071106) {
                if (atts[1] <= 0.094471260905265808) {
                    classes[0] = 1981;
                    classes[1] = 0;
                    classes[2] = 0;
                    classes[3] = 0;
                    classes[4] = 0;
                    classes[5] = 0;
                    classes[6] = 0;
                } else {
                    if (atts[1] <= 0.094876527786254883) {
                        classes[0] = 0;
                        classes[1] = 1;
                        classes[2] = 0;
                        classes[3] = 0;
                        classes[4] = 0;
                        classes[5] = 0;
                        classes[6] = 0;
                    } else {
                        classes[0] = 236;
                        classes[1] = 0;
                        classes[2] = 0;
                        classes[3] = 0;
                        classes[4] = 0;
                        classes[5] = 0;
                        classes[6] = 0;
                    }
                }
            } else {
                if (atts[2] <= 0.89076751470565796) {
                    classes[0] = 27;
                    classes[1] = 0;
                    classes[2] = 0;
                    classes[3] = 0;
                    classes[4] = 0;
                    classes[5] = 0;
                    classes[6] = 0;
                } else {
                    classes[0] = 0;
                    classes[1] = 2;
                    classes[2] = 0;
                    classes[3] = 0;
                    classes[4] = 0;
                    classes[5] = 0;
                    classes[6] = 0;
                }
            }
        } else {
            if (atts[2] <= 0.81128525733947754) {
                if (atts[2] <= 0.43489593267440796) {
                    if (atts[2] <= 0.2557169497013092) {
                        if (atts[1] <= 8.0001850128173828) {
                            if (atts[2] <= 0.23998300731182098) {
                                classes[0] = 107;
                                classes[1] = 0;
                                classes[2] = 0;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            } else {
                                if (atts[2] <= 0.24091917276382446) {
                                    classes[0] = 0;
                                    classes[1] = 1;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                } else {
                                    classes[0] = 6;
                                    classes[1] = 0;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                }
                            }
                        } else {
                            if (atts[0] <= 8.7782802581787109) {
                                if (atts[3] <= 18.362979888916016) {
                                    if (atts[1] <= 9.7615013122558594) {
                                        classes[0] = 0;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 1;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    } else {
                                        classes[0] = 0;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 1;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    }
                                } else {
                                    if (atts[0] <= 8.4563331604003906) {
                                        classes[0] = 3;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    } else {
                                        classes[0] = 0;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 1;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    }
                                }
                            } else {
                                classes[0] = 28;
                                classes[1] = 0;
                                classes[2] = 0;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            }
                        }
                    } else {
                        if (atts[1] <= 1.6492538452148437) {
                            classes[0] = 38;
                            classes[1] = 0;
                            classes[2] = 0;
                            classes[3] = 0;
                            classes[4] = 0;
                            classes[5] = 0;
                            classes[6] = 0;
                        } else {
                            if (atts[1] <= 5.8025469779968262) {
                                if (atts[2] <= 0.29270318150520325) {
                                    if (atts[0] <= 2.9753150939941406) {
                                        if (atts[1] <= 2.2168998718261719) {
                                            classes[0] = 2;
                                            classes[1] = 0;
                                            classes[2] = 0;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        } else {
                                            if (atts[1] <= 2.4897556304931641) {
                                                classes[0] = 0;
                                                classes[1] = 1;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                classes[0] = 1;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        }
                                    } else {
                                        if (atts[2] <= 0.27249708771705627) {
                                            classes[0] = 0;
                                            classes[1] = 0;
                                            classes[2] = 2;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        } else {
                                            if (atts[2] <= 0.28276342153549194) {
                                                classes[0] = 1;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 1;
                                            }
                                        }
                                    }
                                } else {
                                    if (atts[1] <= 2.6285767555236816) {
                                        if (atts[2] <= 0.37588232755661011) {
                                            if (atts[2] <= 0.32323771715164185) {
                                                classes[0] = 3;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                if (atts[0] <= 1.8666480779647827) {
                                                    classes[0] = 1;
                                                    classes[1] = 0;
                                                    classes[2] = 0;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 2;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            }
                                        } else {
                                            if (atts[2] <= 0.4292595386505127) {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 4;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 1;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        }
                                    } else {
                                        if (atts[0] <= 2.1958823204040527) {
                                            if (atts[2] <= 0.39287310838699341) {
                                                classes[0] = 0;
                                                classes[1] = 1;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 1;
                                                classes[6] = 0;
                                            }
                                        } else {
                                            if (atts[3] <= 20.159084320068359) {
                                                if (atts[3] <= 18.898303985595703) {
                                                    if (atts[1] <= 3.9122319221496582) {
                                                        classes[0] = 0;
                                                        classes[1] = 0;
                                                        classes[2] = 0;
                                                        classes[3] = 0;
                                                        classes[4] = 1;
                                                        classes[5] = 0;
                                                        classes[6] = 0;
                                                    } else {
                                                        if (atts[1] <= 4.2428646087646484) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 1;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            classes[0] = 1;
                                                            classes[1] = 0;
                                                            classes[2] = 0;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        }
                                                    }
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 0;
                                                    classes[3] = 0;
                                                    classes[4] = 5;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            } else {
                                                if (atts[3] <= 20.293058395385742) {
                                                    if (atts[0] <= 2.746823787689209) {
                                                        classes[0] = 0;
                                                        classes[1] = 0;
                                                        classes[2] = 0;
                                                        classes[3] = 2;
                                                        classes[4] = 0;
                                                        classes[5] = 0;
                                                        classes[6] = 0;
                                                    } else {
                                                        classes[0] = 0;
                                                        classes[1] = 0;
                                                        classes[2] = 0;
                                                        classes[3] = 0;
                                                        classes[4] = 0;
                                                        classes[5] = 1;
                                                        classes[6] = 0;
                                                    }
                                                } else {
                                                    if (atts[2] <= 0.35312652587890625) {
                                                        if (atts[3] <= 20.673606872558594) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 0;
                                                            classes[3] = 0;
                                                            classes[4] = 1;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            if (atts[3] <= 22.97137451171875) {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 0;
                                                                classes[3] = 1;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 0;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 1;
                                                                classes[6] = 0;
                                                            }
                                                        }
                                                    } else {
                                                        if (atts[0] <= 2.577970027923584) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 0;
                                                            classes[3] = 0;
                                                            classes[4] = 3;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            if (atts[0] <= 2.7807979583740234) {
                                                                classes[0] = 2;
                                                                classes[1] = 0;
                                                                classes[2] = 0;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 0;
                                                                classes[3] = 0;
                                                                classes[4] = 1;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (atts[2] <= 0.28344696760177612) {
                                    if (atts[3] <= 63.690238952636719) {
                                        classes[0] = 0;
                                        classes[1] = 0;
                                        classes[2] = 1;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    } else {
                                        classes[0] = 1;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    }
                                } else {
                                    classes[0] = 12;
                                    classes[1] = 0;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                }
                            }
                        }
                    }
                } else {
                    if (atts[1] <= 0.97585189342498779) {
                        if (atts[1] <= 0.53751295804977417) {
                            classes[0] = 11;
                            classes[1] = 0;
                            classes[2] = 0;
                            classes[3] = 0;
                            classes[4] = 0;
                            classes[5] = 0;
                            classes[6] = 0;
                        } else {
                            if (atts[2] <= 0.5938379168510437) {
                                classes[0] = 4;
                                classes[1] = 0;
                                classes[2] = 0;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            } else {
                                if (atts[3] <= 15.248173713684082) {
                                    classes[0] = 1;
                                    classes[1] = 0;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                } else {
                                    if (atts[0] <= 0.9934394359588623) {
                                        classes[0] = 0;
                                        classes[1] = 5;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    } else {
                                        classes[0] = 1;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    }
                                }
                            }
                        }
                    } else {
                        if (atts[0] <= 2.2029485702514648) {
                            if (atts[1] <= 1.6895006895065308) {
                                if (atts[2] <= 0.58236426115036011) {
                                    if (atts[3] <= 20.017942428588867) {
                                        if (atts[1] <= 1.457252025604248) {
                                            if (atts[0] <= 1.2995202541351318) {
                                                if (atts[0] <= 1.1835364103317261) {
                                                    classes[0] = 1;
                                                    classes[1] = 0;
                                                    classes[2] = 0;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 5;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            } else {
                                                classes[0] = 3;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        } else {
                                            classes[0] = 0;
                                            classes[1] = 0;
                                            classes[2] = 4;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        }
                                    } else {
                                        if (atts[2] <= 0.56869310140609741) {
                                            classes[0] = 0;
                                            classes[1] = 0;
                                            classes[2] = 5;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        } else {
                                            if (atts[1] <= 1.2883014678955078) {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 1;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 1;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        }
                                    }
                                } else {
                                    if (atts[0] <= 1.1184139251708984) {
                                        if (atts[3] <= 17.736637115478516) {
                                            if (atts[1] <= 1.2864181995391846) {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 3;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                if (atts[3] <= 17.042964935302734) {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 0;
                                                    classes[3] = 1;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 1;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            }
                                        } else {
                                            if (atts[2] <= 0.73919987678527832) {
                                                if (atts[1] <= 1.2965290546417236) {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 2;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 1;
                                                    classes[2] = 0;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            } else {
                                                if (atts[1] <= 1.3742392063140869) {
                                                    classes[0] = 0;
                                                    classes[1] = 12;
                                                    classes[2] = 0;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 1;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            }
                                        }
                                    } else {
                                        if (atts[2] <= 0.58631646633148193) {
                                            classes[0] = 0;
                                            classes[1] = 2;
                                            classes[2] = 0;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        } else {
                                            if (atts[3] <= 29.219564437866211) {
                                                if (atts[2] <= 0.80338209867477417) {
                                                    if (atts[1] <= 1.640245795249939) {
                                                        if (atts[1] <= 1.5118763446807861) {
                                                            if (atts[1] <= 1.4704504013061523) {
                                                                if (atts[1] <= 1.2666449546813965) {
                                                                    if (atts[3] <= 17.423610687255859) {
                                                                        classes[0] = 0;
                                                                        classes[1] = 2;
                                                                        classes[2] = 0;
                                                                        classes[3] = 0;
                                                                        classes[4] = 0;
                                                                        classes[5] = 0;
                                                                        classes[6] = 0;
                                                                    } else {
                                                                        if (atts[1] <= 1.2511132955551147) {
                                                                            classes[0] = 0;
                                                                            classes[1] = 0;
                                                                            classes[2] = 6;
                                                                            classes[3] = 0;
                                                                            classes[4] = 0;
                                                                            classes[5] = 0;
                                                                            classes[6] = 0;
                                                                        } else {
                                                                            classes[0] = 0;
                                                                            classes[1] = 1;
                                                                            classes[2] = 0;
                                                                            classes[3] = 0;
                                                                            classes[4] = 0;
                                                                            classes[5] = 0;
                                                                            classes[6] = 0;
                                                                        }
                                                                    }
                                                                } else {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 23;
                                                                    classes[3] = 0;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                }
                                                            } else {
                                                                if (atts[0] <= 1.2365896701812744) {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 2;
                                                                    classes[3] = 0;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                } else {
                                                                    classes[0] = 0;
                                                                    classes[1] = 3;
                                                                    classes[2] = 0;
                                                                    classes[3] = 0;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                }
                                                            }
                                                        } else {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 18;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        }
                                                    } else {
                                                        if (atts[0] <= 1.2217395305633545) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 1;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            classes[0] = 0;
                                                            classes[1] = 2;
                                                            classes[2] = 0;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        }
                                                    }
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 2;
                                                    classes[2] = 0;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 2;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (atts[2] <= 0.66329413652420044) {
                                    if (atts[1] <= 2.8622770309448242) {
                                        if (atts[3] <= 30.290332794189453) {
                                            if (atts[1] <= 1.8830153942108154) {
                                                if (atts[3] <= 19.467361450195313) {
                                                    if (atts[3] <= 18.452096939086914) {
                                                        classes[0] = 0;
                                                        classes[1] = 0;
                                                        classes[2] = 0;
                                                        classes[3] = 5;
                                                        classes[4] = 0;
                                                        classes[5] = 0;
                                                        classes[6] = 0;
                                                    } else {
                                                        if (atts[0] <= 1.5656218528747559) {
                                                            classes[0] = 0;
                                                            classes[1] = 2;
                                                            classes[2] = 0;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 0;
                                                            classes[3] = 1;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        }
                                                    }
                                                } else {
                                                    if (atts[2] <= 0.59437191486358643) {
                                                        if (atts[1] <= 1.7766807079315186) {
                                                            if (atts[2] <= 0.56414520740509033) {
                                                                classes[0] = 0;
                                                                classes[1] = 2;
                                                                classes[2] = 0;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 2;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            }
                                                        } else {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 4;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        }
                                                    } else {
                                                        if (atts[2] <= 0.61463606357574463) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 0;
                                                            classes[3] = 2;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            if (atts[0] <= 1.4646484851837158) {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 3;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                if (atts[2] <= 0.62989699840545654) {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 1;
                                                                    classes[3] = 0;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                } else {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 0;
                                                                    classes[3] = 1;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (atts[2] <= 0.45663756132125854) {
                                                    if (atts[1] <= 2.2405922412872314) {
                                                        if (atts[1] <= 1.9789766073226929) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 1;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            if (atts[1] <= 2.0375595092773437) {
                                                                classes[0] = 0;
                                                                classes[1] = 1;
                                                                classes[2] = 0;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                if (atts[2] <= 0.45198339223861694) {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 0;
                                                                    classes[3] = 0;
                                                                    classes[4] = 2;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                } else {
                                                                    classes[0] = 0;
                                                                    classes[1] = 1;
                                                                    classes[2] = 0;
                                                                    classes[3] = 0;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        if (atts[3] <= 19.943439483642578) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 1;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 0;
                                                            classes[3] = 2;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        }
                                                    }
                                                } else {
                                                    if (atts[3] <= 22.006765365600586) {
                                                        if (atts[3] <= 18.480690002441406) {
                                                            if (atts[1] <= 2.0422008037567139) {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 0;
                                                                classes[3] = 0;
                                                                classes[4] = 2;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 3;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            }
                                                        } else {
                                                            if (atts[0] <= 1.9507513046264648) {
                                                                if (atts[3] <= 20.258441925048828) {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 15;
                                                                    classes[3] = 0;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                } else {
                                                                    if (atts[3] <= 20.349308013916016) {
                                                                        classes[0] = 0;
                                                                        classes[1] = 0;
                                                                        classes[2] = 0;
                                                                        classes[3] = 2;
                                                                        classes[4] = 0;
                                                                        classes[5] = 0;
                                                                        classes[6] = 0;
                                                                    } else {
                                                                        classes[0] = 0;
                                                                        classes[1] = 0;
                                                                        classes[2] = 6;
                                                                        classes[3] = 0;
                                                                        classes[4] = 0;
                                                                        classes[5] = 0;
                                                                        classes[6] = 0;
                                                                    }
                                                                }
                                                            } else {
                                                                if (atts[2] <= 0.48542112112045288) {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 2;
                                                                    classes[3] = 0;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                } else {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 0;
                                                                    classes[3] = 3;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        if (atts[1] <= 2.6876068115234375) {
                                                            if (atts[3] <= 22.462976455688477) {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 0;
                                                                classes[3] = 2;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                if (atts[2] <= 0.51857328414916992) {
                                                                    classes[0] = 0;
                                                                    classes[1] = 0;
                                                                    classes[2] = 2;
                                                                    classes[3] = 0;
                                                                    classes[4] = 0;
                                                                    classes[5] = 0;
                                                                    classes[6] = 0;
                                                                } else {
                                                                    if (atts[2] <= 0.59647464752197266) {
                                                                        classes[0] = 0;
                                                                        classes[1] = 0;
                                                                        classes[2] = 0;
                                                                        classes[3] = 3;
                                                                        classes[4] = 0;
                                                                        classes[5] = 0;
                                                                        classes[6] = 0;
                                                                    } else {
                                                                        if (atts[3] <= 23.519668579101563) {
                                                                            classes[0] = 0;
                                                                            classes[1] = 0;
                                                                            classes[2] = 0;
                                                                            classes[3] = 1;
                                                                            classes[4] = 0;
                                                                            classes[5] = 0;
                                                                            classes[6] = 0;
                                                                        } else {
                                                                            classes[0] = 0;
                                                                            classes[1] = 0;
                                                                            classes[2] = 2;
                                                                            classes[3] = 0;
                                                                            classes[4] = 0;
                                                                            classes[5] = 0;
                                                                            classes[6] = 0;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 0;
                                                            classes[3] = 0;
                                                            classes[4] = 2;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            if (atts[2] <= 0.52979278564453125) {
                                                classes[0] = 1;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 1;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        }
                                    } else {
                                        if (atts[3] <= 18.193424224853516) {
                                            if (atts[1] <= 3.2312150001525879) {
                                                classes[0] = 1;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 1;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        } else {
                                            if (atts[2] <= 0.65407705307006836) {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 7;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                classes[0] = 1;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        }
                                    }
                                } else {
                                    if (atts[2] <= 0.75422239303588867) {
                                        if (atts[2] <= 0.72400665283203125) {
                                            if (atts[1] <= 3.0536105632781982) {
                                                if (atts[2] <= 0.71545159816741943) {
                                                    if (atts[2] <= 0.68102180957794189) {
                                                        if (atts[3] <= 19.803569793701172) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 4;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            if (atts[0] <= 1.4219262599945068) {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 1;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                classes[0] = 0;
                                                                classes[1] = 2;
                                                                classes[2] = 0;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            }
                                                        }
                                                    } else {
                                                        classes[0] = 0;
                                                        classes[1] = 0;
                                                        classes[2] = 9;
                                                        classes[3] = 0;
                                                        classes[4] = 0;
                                                        classes[5] = 0;
                                                        classes[6] = 0;
                                                    }
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 1;
                                                    classes[2] = 0;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 1;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        } else {
                                            classes[0] = 0;
                                            classes[1] = 0;
                                            classes[2] = 12;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        }
                                    } else {
                                        if (atts[3] <= 17.803043365478516) {
                                            classes[0] = 0;
                                            classes[1] = 1;
                                            classes[2] = 0;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        } else {
                                            if (atts[2] <= 0.76179248094558716) {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 0;
                                                classes[3] = 2;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                if (atts[0] <= 1.4200007915496826) {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 10;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                } else {
                                                    if (atts[1] <= 2.4154074192047119) {
                                                        if (atts[2] <= 0.77237200736999512) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 1;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 0;
                                                            classes[3] = 3;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        }
                                                    } else {
                                                        classes[0] = 0;
                                                        classes[1] = 0;
                                                        classes[2] = 2;
                                                        classes[3] = 0;
                                                        classes[4] = 0;
                                                        classes[5] = 0;
                                                        classes[6] = 0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (atts[2] <= 0.50300252437591553) {
                                if (atts[2] <= 0.47261756658554077) {
                                    classes[0] = 2;
                                    classes[1] = 0;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                } else {
                                    if (atts[1] <= 4.3999037742614746) {
                                        classes[0] = 0;
                                        classes[1] = 0;
                                        classes[2] = 1;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    } else {
                                        classes[0] = 0;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 1;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    }
                                }
                            } else {
                                classes[0] = 13;
                                classes[1] = 0;
                                classes[2] = 0;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            }
                        }
                    }
                }
            } else {
                if (atts[2] <= 0.86208558082580566) {
                    if (atts[1] <= 0.92630457878112793) {
                        if (atts[1] <= 0.69354695081710815) {
                            if (atts[1] <= 0.52815794944763184) {
                                classes[0] = 1;
                                classes[1] = 0;
                                classes[2] = 0;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            } else {
                                if (atts[1] <= 0.68351626396179199) {
                                    classes[0] = 0;
                                    classes[1] = 2;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                } else {
                                    classes[0] = 1;
                                    classes[1] = 0;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                }
                            }
                        } else {
                            classes[0] = 0;
                            classes[1] = 11;
                            classes[2] = 0;
                            classes[3] = 0;
                            classes[4] = 0;
                            classes[5] = 0;
                            classes[6] = 0;
                        }
                    } else {
                        if (atts[3] <= 18.602375030517578) {
                            if (atts[0] <= 1.0851966142654419) {
                                classes[0] = 0;
                                classes[1] = 0;
                                classes[2] = 9;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            } else {
                                if (atts[3] <= 18.46330451965332) {
                                    if (atts[2] <= 0.82260727882385254) {
                                        classes[0] = 0;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 1;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    } else {
                                        classes[0] = 0;
                                        classes[1] = 4;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    }
                                } else {
                                    classes[0] = 0;
                                    classes[1] = 0;
                                    classes[2] = 1;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                }
                            }
                        } else {
                            if (atts[0] <= 1.3260998725891113) {
                                if (atts[0] <= 0.89692950248718262) {
                                    classes[0] = 0;
                                    classes[1] = 0;
                                    classes[2] = 1;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                } else {
                                    if (atts[0] <= 1.0912351608276367) {
                                        if (atts[2] <= 0.85145747661590576) {
                                            classes[0] = 0;
                                            classes[1] = 19;
                                            classes[2] = 0;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        } else {
                                            if (atts[2] <= 0.85300672054290771) {
                                                classes[0] = 0;
                                                classes[1] = 0;
                                                classes[2] = 1;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            } else {
                                                if (atts[1] <= 1.4747042655944824) {
                                                    classes[0] = 0;
                                                    classes[1] = 10;
                                                    classes[2] = 0;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                } else {
                                                    if (atts[1] <= 1.529630184173584) {
                                                        classes[0] = 0;
                                                        classes[1] = 0;
                                                        classes[2] = 1;
                                                        classes[3] = 0;
                                                        classes[4] = 0;
                                                        classes[5] = 0;
                                                        classes[6] = 0;
                                                    } else {
                                                        classes[0] = 0;
                                                        classes[1] = 1;
                                                        classes[2] = 0;
                                                        classes[3] = 0;
                                                        classes[4] = 0;
                                                        classes[5] = 0;
                                                        classes[6] = 0;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (atts[3] <= 19.931427001953125) {
                                            classes[0] = 0;
                                            classes[1] = 5;
                                            classes[2] = 0;
                                            classes[3] = 0;
                                            classes[4] = 0;
                                            classes[5] = 0;
                                            classes[6] = 0;
                                        } else {
                                            if (atts[0] <= 1.2358944416046143) {
                                                if (atts[1] <= 1.6358274221420288) {
                                                    if (atts[2] <= 0.85712730884552002) {
                                                        if (atts[0] <= 1.1012551784515381) {
                                                            classes[0] = 0;
                                                            classes[1] = 0;
                                                            classes[2] = 2;
                                                            classes[3] = 0;
                                                            classes[4] = 0;
                                                            classes[5] = 0;
                                                            classes[6] = 0;
                                                        } else {
                                                            if (atts[2] <= 0.85518485307693481) {
                                                                classes[0] = 0;
                                                                classes[1] = 3;
                                                                classes[2] = 0;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            } else {
                                                                classes[0] = 0;
                                                                classes[1] = 0;
                                                                classes[2] = 1;
                                                                classes[3] = 0;
                                                                classes[4] = 0;
                                                                classes[5] = 0;
                                                                classes[6] = 0;
                                                            }
                                                        }
                                                    } else {
                                                        classes[0] = 0;
                                                        classes[1] = 2;
                                                        classes[2] = 0;
                                                        classes[3] = 0;
                                                        classes[4] = 0;
                                                        classes[5] = 0;
                                                        classes[6] = 0;
                                                    }
                                                } else {
                                                    classes[0] = 0;
                                                    classes[1] = 0;
                                                    classes[2] = 2;
                                                    classes[3] = 0;
                                                    classes[4] = 0;
                                                    classes[5] = 0;
                                                    classes[6] = 0;
                                                }
                                            } else {
                                                classes[0] = 0;
                                                classes[1] = 2;
                                                classes[2] = 0;
                                                classes[3] = 0;
                                                classes[4] = 0;
                                                classes[5] = 0;
                                                classes[6] = 0;
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (atts[2] <= 0.85346269607543945) {
                                    if (atts[1] <= 1205.93896484375) {
                                        classes[0] = 0;
                                        classes[1] = 0;
                                        classes[2] = 3;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    } else {
                                        classes[0] = 1;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    }
                                } else {
                                    classes[0] = 0;
                                    classes[1] = 1;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                }
                            }
                        }
                    }
                } else {
                    if (atts[3] <= 17.082454681396484) {
                        if (atts[0] <= 1.1133818626403809) {
                            if (atts[3] <= 17.068609237670898) {
                                if (atts[0] <= 0.61941635608673096) {
                                    if (atts[0] <= 0.61303317546844482) {
                                        classes[0] = 0;
                                        classes[1] = 4;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    } else {
                                        classes[0] = 1;
                                        classes[1] = 0;
                                        classes[2] = 0;
                                        classes[3] = 0;
                                        classes[4] = 0;
                                        classes[5] = 0;
                                        classes[6] = 0;
                                    }
                                } else {
                                    classes[0] = 0;
                                    classes[1] = 119;
                                    classes[2] = 0;
                                    classes[3] = 0;
                                    classes[4] = 0;
                                    classes[5] = 0;
                                    classes[6] = 0;
                                }
                            } else {
                                classes[0] = 0;
                                classes[1] = 0;
                                classes[2] = 1;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            }
                        } else {
                            if (atts[2] <= 0.87508654594421387) {
                                classes[0] = 0;
                                classes[1] = 0;
                                classes[2] = 2;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            } else {
                                classes[0] = 0;
                                classes[1] = 1;
                                classes[2] = 0;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            }
                        }
                    } else {
                        if (atts[3] <= 20.803272247314453) {
                            classes[0] = 0;
                            classes[1] = 393;
                            classes[2] = 0;
                            classes[3] = 0;
                            classes[4] = 0;
                            classes[5] = 0;
                            classes[6] = 0;
                        } else {
                            if (atts[3] <= 20.812858581542969) {
                                classes[0] = 2;
                                classes[1] = 0;
                                classes[2] = 0;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            } else {
                                classes[0] = 0;
                                classes[1] = 311;
                                classes[2] = 0;
                                classes[3] = 0;
                                classes[4] = 0;
                                classes[5] = 0;
                                classes[6] = 0;
                            }
                        }
                    }
                }
            }
        }

        int class_idx = 0;
        int class_val = classes[0];
        for (int i = 1; i < 7; i++) {
            if (classes[i] > class_val) {
                class_idx = i;
                class_val = classes[i];
            }
        }
        return class_idx;
    }

    public static void calc_avgs(List<MatOfPoint> contours) {
        // find avg area and perim needed for normalizing
        double perim;
        double area;
        int total_perim = 0;
        int total_area = 0;
        int cnt = 0;

        for (MatOfPoint cont : contours) {
            //These values are about the range that colony blobs have
            //These don't need to be too exact, as long as we dont pick
            //up the extremely small/big contours
            MatOfPoint2f fcont = new MatOfPoint2f(cont.toArray());
            perim = Imgproc.arcLength(fcont, true);
            area = Imgproc.contourArea(fcont);
            if (perim < 700 && perim > 100 && area > 300 && area < 10000) {
                total_perim += perim;
                total_area += area;
                cnt++;
            }
        }
        avg_perim = total_perim / cnt;
        avg_area = total_area / cnt;
    }

    public static void generate_feats(Mat orig, List<MatOfPoint> contours) {
        Mat gray = new Mat();
        Mat hsv_img = new Mat();
        Mat tmp = new Mat();
        List<Mat> hsv = new ArrayList<Mat>(3);
        Scalar avg_hue = new Scalar(0);
        double features[] = new double[4];
        List<MatOfPoint> colonyContours = new Vector<MatOfPoint>();
        int contPrediction = 0;
        int tempPredict = 0;

        double perim;
        double area;

        Imgproc.cvtColor(orig, gray, Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(orig, hsv_img, Imgproc.COLOR_RGB2HSV);
        Core.split(hsv_img, hsv);
        Mat hue = hsv.get(0);

        calc_avgs(contours);
        //display each contour, grab perim, area, and color
        for (int ndx = 0; ndx < contours.size(); ndx++) {
            MatOfPoint2f fcont = new MatOfPoint2f(contours.get(ndx).toArray());
            //get perimeter, area, shape factor of contour
            perim = Imgproc.arcLength(fcont, true);
            area = Imgproc.contourArea(fcont);

            //normalize by mean b/c images with different resolutions
            //can yield different perims/areas
            features[0] = perim / avg_perim;
            features[1] = area / avg_area;

            //a measure of how circular something
            features[2] = 4 * Math.PI * area / Math.pow(perim, 2);

//            //create mask to get avg hue of pixels in contour
            Mat mask = Mat.zeros(orig.size(), CvType.CV_8UC1);
            Imgproc.drawContours(mask, contours, ndx , new Scalar(255, 255, 255), 1);
            features[3] = Core.mean(hue, mask).val[0];
            tempPredict = predict(features);
            if(tempPredict > 0) {
                contPrediction += tempPredict;
                colonyContours.add(contours.get(ndx));
            }
        }

        results.contours = colonyContours;
        results.count = contPrediction;
    }

    public static void runAlgorithm(String inputImage) throws Exception {
        if (instance == null) {
            throw new Exception("instance is null");
        }

        inputImage = inputImage;

        // do algorithm stuff here
        String parent = new File(inputImage).getParent();
        String name = new File(inputImage).getName() + "_Annotated";
        String newPath = parent + "/" + name;
        // Save image as a Mat object
        Mat imgMat = Imgcodecs.imread(inputImage);
        Mat grayMat = new Mat();
        // Grayscale
        Imgproc.cvtColor(imgMat, grayMat, Imgproc.COLOR_RGB2GRAY);

        // Apply CLAHE
        Mat claheMat = new Mat();
        CLAHE clahe = Imgproc.createCLAHE(2.0, new Size(8,8));
        clahe.apply(grayMat, claheMat);

        // Normalize
        Core.normalize(claheMat, claheMat, 0, 100, Core.NORM_MINMAX, CvType.CV_8UC1);
        // Getting the max histogram value
        Vector<Mat> matList = new Vector<Mat>();
        matList.add(claheMat);

        Mat histMat = new Mat();
        Imgproc.calcHist(matList, new MatOfInt(0), new Mat(), histMat, new MatOfInt(256), new MatOfFloat(0, 256));

        // find the max value in the histogram
        histMat.put(0, 0, 0);
        Core.MinMaxLocResult histResults = Core.minMaxLoc(histMat);
        double hist_max = histResults.maxLoc.y;
        int max = (int)hist_max;

        // Threshold based on histogram
        Mat threshMat = new Mat();
        Imgproc.threshold(claheMat, threshMat, max + 20, 100, Imgproc.THRESH_BINARY);
        Imgproc.medianBlur(threshMat, threshMat, 9);

        // Run Morphology
        Imgproc.morphologyEx(threshMat, threshMat, Imgproc.MORPH_OPEN,
                Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3)), new Point(), 2);

        // Find contours
        List<MatOfPoint> contours = new Vector<MatOfPoint>();
        Imgproc.findContours(threshMat, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        // This should be temporary for testing the output image
        generate_feats(imgMat, contours);


        for (int i = 0; i < results.contours.size(); i++) {
            Imgproc.drawContours(imgMat, results.contours, i, new Scalar(0, 255, 0), 3);
        }

        // Write back to new image path
        Imgcodecs.imwrite(newPath, imgMat);
        // report results
        Log.e("TEST", "Counting done, count: " + results.count + ", path: " + newPath);
        listener.algorithmCompleted(results.count, newPath);
    }
}