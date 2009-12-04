import org.jfree.chart.ChartFactory
import org.jfree.data.general.DefaultPieDataset
import org.jfree.chart.encoders.EncoderUtil
import org.jfree.chart.servlet.ServletUtilities
import java.util.Random
import org.jfree.data.general.WaferMapDataset
import org.jfree.chart.plot.PlotOrientation
import java.awt.GradientPaint
import java.awt.Color

class ChartController {

    def pie = {
        log.debug("Creating pie chart with width/height: " + params.width + "/" + params.height)
        // create the data for the pie chart
        def slices = [
            [label:"One", percent:43.2],
            [label:"Two", percent:10.0],
            [label:"Three", percent:27.5],
            [label:"Four", percent:17.5],
            [label:"Five", percent:11.0],
            [label:"Six", percent:19.4]
        ]

        // load the data into a dataset
        def dataset = new DefaultPieDataset();
        slices.each { slice ->
            dataset.setValue(slice.label, slice.percent)
        }

        // create the pie chart and stream it back to the client
        def chart = ChartFactory.createPieChart("Pie Chart Demo 1", dataset, true, true, false)
        def chartName = ServletUtilities.saveChartAsJPEG(chart, Integer.parseInt(params.width), Integer.parseInt(params.height), null, null);        
        render chartName
    }
    
    def wafer = {
        log.debug("Creating wafer chart with width/height: " + params.width + "/" + params.height)
       
        WaferMapDataset dataset = createRandomWaferMapDataset(50)
        
        // create the pie chart and stream it back to the client
        def chart = ChartFactory.createWaferMapChart("Wafer Map Demo", dataset, PlotOrientation.VERTICAL, true, false, false)
        chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000, Color.blue))
        def chartName = ServletUtilities.saveChartAsPNG(chart, Integer.parseInt(params.width), Integer.parseInt(params.height), null, null);
        render chartName
    }
    
    WaferMapDataset createRandomWaferMapDataset(int values) {
        int xdim = 30
        int ydim = 20
        Random random = new Random()
        
        WaferMapDataset data = new WaferMapDataset(xdim, ydim)
        
        for (int x = 1; x <= xdim; x++) {
            for (int y = 0; y < ydim; y++) {
                def val = random.nextInt(values)
                data.addValue(val, x, y)
            }
        }
        
        return data;
    }    
}