package hex.tree.sdt;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataFeaturesLimits {
    // limits for each feature
    private final List<FeatureLimits> _featuresLimits;

    public DataFeaturesLimits(final List<FeatureLimits> featureLimits) {
        this._featuresLimits = featureLimits;
    }

    public DataFeaturesLimits(final double[][] featureLimits) {
        this._featuresLimits = Arrays.stream(featureLimits)
                .map(dd -> new FeatureLimits(dd[0], dd[1]))
                .collect(Collectors.toList());
    }

    public DataFeaturesLimits clone() {
        return new DataFeaturesLimits(_featuresLimits.stream().map(FeatureLimits::clone).collect(Collectors.toList()));
    }

    public DataFeaturesLimits updateMin(final int selectedFeature, final double newMin) {
        DataFeaturesLimits clone = new DataFeaturesLimits(
                _featuresLimits.stream().map(FeatureLimits::clone).collect(Collectors.toList()));
        clone._featuresLimits.get(selectedFeature).setNewMin(newMin);
        return clone;
    }

    public DataFeaturesLimits updateMax(final int selectedFeature, final double newMax) {
        DataFeaturesLimits clone = new DataFeaturesLimits(
                _featuresLimits.stream().map(FeatureLimits::clone).collect(Collectors.toList()));
        clone._featuresLimits.get(selectedFeature).setNewMax(newMax);
        return clone;
    }

    public FeatureLimits getFeatureLimits(int featureIndex) {
        return _featuresLimits.get(featureIndex);
    }

    public Stream<Double> getFeatureRange(final int featureIndex) {
        return _featuresLimits.get(featureIndex).getFeatureRange();
    }

//    public List<Pair<Double, Double>> getFeatureBinsLimits(final int featureIndex) {
//        return featuresLimits.get(featureIndex).getFeatureBinsLimits();
//    }

    public double[][] toDoubles() {
//        System.out.println(featuresLimits.toString());
//        System.out.println(Arrays.deepToString(featuresLimits.stream().map(v -> new double[]{v._min, v._max}).toArray(double[][]::new)));
        return _featuresLimits.stream().map(v -> new double[]{v._min, v._max}).toArray(double[][]::new);
    }
}
