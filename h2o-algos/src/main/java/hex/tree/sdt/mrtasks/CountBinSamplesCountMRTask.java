package hex.tree.sdt.mrtasks;

import org.apache.commons.math3.util.Precision;
import water.MRTask;
import water.fvec.Chunk;

/**
 * MR task for counting samples in bins.
 */
public class CountBinSamplesCountMRTask extends MRTask<CountBinSamplesCountMRTask> {
    public int _featureSplit;
    public double _thresholdMin;
    public double _thresholdMax;
    public int _count;
    public int _count0;
    // numCol x 2 - min and max for each feature
    double[][] _featuresLimits;

    int LIMIT_MIN = 0;
    int LIMIT_MAX = 1;

    public CountBinSamplesCountMRTask(int featureSplit, double thresholdMin,
                                      double thresholdMax, double[][] featuresLimits) {
        _featureSplit = featureSplit;
        _thresholdMin = thresholdMin;
        _thresholdMax = thresholdMax;
        _count = 0;
        _count0 = 0;
        _featuresLimits = featuresLimits;
    }

    @Override
    public void map(Chunk[] cs) {
        int classFeature = cs.length - 1;
        int numRows = cs[0]._len;
        boolean conditionsFailed;
        // select only rows that fulfill all conditions
        for (int row = 0; row < numRows; row++) {
            conditionsFailed = false;
            for (int column = 0; column < cs.length - 1 /*exclude prediction column*/; column++) {
                // if the value is out of the given limit, skip this row
                if (cs[column].atd(row) <= _featuresLimits[column][LIMIT_MIN]
                        || cs[column].atd(row) > _featuresLimits[column][LIMIT_MAX]) {
                    conditionsFailed = true;
                    break;
                }
            }
            if (!conditionsFailed) {
                // count feature values in the current bin
                if ((cs[_featureSplit].atd(row) > _thresholdMin)
                        && (cs[_featureSplit].atd(row) < _thresholdMax
                        || Precision.equals(cs[_featureSplit].atd(row), _thresholdMax, Precision.EPSILON))) {
                    _count++;
                    if (Precision.equals(cs[classFeature].atd(row), 0, Precision.EPSILON)) {
                        _count0++;
                    }
                }

            }
        }
    }

    @Override
    public void reduce(CountBinSamplesCountMRTask mrt) {
        _count += mrt._count;
        _count0 += mrt._count0;
    }
}
