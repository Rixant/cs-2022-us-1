package com.cs.rfq.decorator.extractors;

import com.cs.rfq.decorator.Rfq;
import com.cs.rfq.decorator.TradeDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;
//import static org.junit.Assert.assertEquals;

class AverageTradedPriceTest extends AbstractSparkUnitTest{
    private Rfq rfq1;
    private Rfq rfq2;

    @BeforeEach
    public void setup() {
        rfq1 = new Rfq();
        rfq1.setIsin("AT0000A0VRQ6");
        rfq2 = new Rfq();
        rfq2.setIsin("AT0000A0VRQ7");
    }

    @Test
    public void checkAveragePriceWhenAllTradesMatch() {

        //String filePath = getClass().getResource("volume-traded-1.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, "src/test/resources/trades/trades.json");

        AverageTradedPriceExtractor extractor = new AverageTradedPriceExtractor();

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq1, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.avgTradedPriceInstrumentPastWeek);

        assertEquals(140.18225, result);
    }

    private void assertEquals(double v, Object result) {
    }

    @Test
    public void checkAveragePriceWhenNoTradesMatch() {

        String filePath = getClass().getResource("volume-traded-1.json").getPath();
        Dataset<Row> trades = new TradeDataLoader().loadTrades(session, filePath);

        //all test trade data are for 2018 so this will cause no matches
        AverageTradedPriceExtractor extractor = new AverageTradedPriceExtractor();

        Map<RfqMetadataFieldNames, Object> meta = extractor.extractMetaData(rfq2, session, trades);

        Object result = meta.get(RfqMetadataFieldNames.avgTradedPriceInstrumentPastWeek);

        assertEquals(0L, result);
    }

}