package hex.schemas;

import hex.isotonic.IsotonicRegression;
import hex.isotonic.IsotonicRegressionModel;
import water.api.schemas3.ModelParametersSchemaV3;

public class IsotonicRegressionV3 extends ModelBuilderSchema<IsotonicRegression, IsotonicRegressionV3, IsotonicRegressionV3.IsotonicRegressionParametersV3> {

    public static final class IsotonicRegressionParametersV3 
            extends ModelParametersSchemaV3<IsotonicRegressionModel.IsotonicRegressionParameters, IsotonicRegressionV3.IsotonicRegressionParametersV3> {
        public static String[] fields = new String[]{
                "model_id",
                "training_frame",
                "response_column",
                "ignored_columns",
                "weights_column",
        };
    }

}
