package example.com.expressapp.send.view;

//2016-8-15 11:40
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Unit;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.TextSymbol;
import com.esri.core.tasks.geocode.Locator;
import com.esri.core.tasks.geocode.LocatorFindParameters;
import com.esri.core.tasks.geocode.LocatorGeocodeResult;
import com.esri.core.tasks.na.NAFeaturesAsFeature;
import com.esri.core.tasks.na.Route;
import com.esri.core.tasks.na.RouteDirection;
import com.esri.core.tasks.na.RouteParameters;
import com.esri.core.tasks.na.RouteResult;
import com.esri.core.tasks.na.RouteTask;
import com.esri.core.tasks.na.StopGraphic;

import example.com.expressapp.send.model.ExpressDistance;
import example.com.expressapp.send.model.AntColonyAlgorithm;
import example.com.expressapp.R;
import example.com.expressapp.send.model.MyAdapter;


public class SendActivity extends AppCompatActivity implements
        RoutingListFragment.onDrawerListSelectedListener,SearchView.OnQueryTextListener{


    MapView mMapView;
    GraphicsLayer mLocationLayer;
    GraphicsLayer mSearchResultLayer;
    Point mSearchResultLayerPoint;
    GraphicsLayer routeLayer, hiddenSegmentsLayer;

    ProgressDialog dialog;
    AppCompatButton nextButton;
    AppCompatButton belowButton;
    TextView directionsLabel;
    CardView calculateCardView;
    CardView getDirectionCardView;
    public static DrawerLayout mDrawerLayout;
    private Toolbar toolbar;

    List<Point> mLocationLayerPoint = new ArrayList<>();
    String mLocationLayerPointString;
    boolean mIsMapLoaded;
    boolean mIsAddressLoaded = false;
    boolean allAddressLoadedInWGS84 = false;
    int selectedSegmentID = -1;
    String mSearchResultLayerPointString;

    Locator mLocator;
    LocationDisplayManager mLDM;
    LocationDisplayManager ldm;
    final static double ZOOM_BY = 20;
    SpatialReference mMapSr = null;

    final SpatialReference wm = SpatialReference.create(102100);
    final SpatialReference egs = SpatialReference.create(4326);
    String source;
    String destination;
    double totalTime = 0;
    double totalLength = 0;

    // Current route, route summary, and gps location
    Route curRoute = null;
    String routeSummary = null;
    public static Point mLocation = null;
    Envelope allAddressExtent = null;

    // Global results variable for calculating route on separate thread
    RouteTask mRouteTask = null;
    List<RouteResult> mResults = new ArrayList<>();
    // Variable to hold server exception to show to user
    Exception mException = null;
    // List of the directions for the current route (used for the ListActivity)
    //原来public static ArrayList<String> curDirections = null;
    public static ArrayList<String> curDirections = new ArrayList<>();


    public LocationManager manager;
    // Symbol used to make route segments "invisible"
    SimpleLineSymbol segmentHider = new SimpleLineSymbol(Color.WHITE, 5);
    // Symbol used to highlight route segments
    SimpleLineSymbol segmentShower = new SimpleLineSymbol(Color.RED, 5);

    final Handler mHandler = new Handler();
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateUI();
        }
    };

    //Store addressList
    List<String> addressList = new ArrayList<>();//need not to clear
    int [] singleAddressNum = null;//need not to clear

    //Store GeocodeResult
    List<LocatorGeocodeResult> allGeocodeResults = new ArrayList<>();//need not to clear
    List<Point> allPointsForThread = new ArrayList<>();//need not to clear
    ExpressDistance[] allSingleRoute = null;

    int [] shortestRoute = null;
    List<Point> shortestRoutePoints = new ArrayList<>();

    final OnStatusChangedListener statusChangedListener = new OnStatusChangedListener() {

        private static final long serialVersionUID = 1L;

        @Override
        public void onStatusChanged(Object source, STATUS status) {

            if (source == mMapView && status == STATUS.INITIALIZED) {
                mIsMapLoaded = true;
                mMapSr = mMapView.getSpatialReference();
                if (mLDM == null) {
                    setupLocationListener();
                }
            }
        }
    };

    public void addressInit(){
        addressList.add("6128 Wilcrest Dr.");
        addressList.add("9600 Bellaire Blvd. Suite 101");
        //addressList.add("9600 Bellaire Blvd. Suite 252");
/*        addressList.add("4800 Calhoun Road, Houston, Texas 77004");
        addressList.add("Louisiana St, Houston, Texas");
        addressList.add("Franklin St, Houston, Texas");
        addressList.add("Silver St, Houston, Texas");
        addressList.add("Panama St, Houston, Texas");*/
    }

    public void singleAddressNumInit(){
        int temp = addressList.size();
        singleAddressNum = new int [temp];
        for(int i = 0; i < temp; i++){
            singleAddressNum[i] = i;
        }
    }

    public void singleRouteInit(){
        //计算任意2点间路径的数量
        int num = addressList.size();
        num *= (num - 1);
        num /= 2;
        allSingleRoute = new ExpressDistance[num];
    }

/*    public void onSearchButtonClicked(View view){
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        String address = mSearchEditText.getText().toString();
        executePlaceSearchTask(address);
    }*/

    private void executePlaceSearchTask(String address) {
        // Create Locator parameters from single line addressList string
        LocatorFindParameters findParams = new LocatorFindParameters(address);

        // Use the centre of the current map extent as the find location point
        findParams.setLocation(mMapView.getCenter(), mMapView.getSpatialReference());

        // Calculate distance for find operation
        Envelope mapExtent = new Envelope();
        mMapView.getExtent().queryEnvelope(mapExtent);
        // assume map is in metres, other units wont work, double current envelope
        double distance = (mapExtent != null && mapExtent.getWidth() > 0) ? mapExtent.getWidth() * 2 : 10000;
        findParams.setDistance(distance);
        findParams.setMaxLocations(2);

        // Set addressList spatial reference to match map
        findParams.setOutSR(mMapView.getSpatialReference());

        // Execute async task to find the addressList
        new PlaceSearchAsyncTask().execute(findParams);
        mLocationLayerPointString = address;
    }

    private class PlaceSearchAsyncTask extends AsyncTask<LocatorFindParameters, Void, List<LocatorGeocodeResult>> {
        private Exception mException;

        @Override
        protected List<LocatorGeocodeResult> doInBackground(LocatorFindParameters... params) {
            mException = null;
            List<LocatorGeocodeResult> results = null;
            Locator locator = Locator.createOnlineLocator();
            try {
                results = locator.find(params[0]);
            } catch (Exception e) {
                mException = e;
            }
            return results;
        }
        protected void onPostExecute(List<LocatorGeocodeResult> result) {
            if (mException != null) {
                Log.w("PlaceSearch", "LocatorSyncTask failed with:");
                mException.printStackTrace();
                Toast.makeText(SendActivity.this,"不能找到该地址", Toast.LENGTH_LONG).show();
                return;
            }

            if (result.size() == 0) {
                Toast.makeText(SendActivity.this,"没有找到该地址", Toast.LENGTH_LONG).show();
            } else {
                //remove last placesearch result
                mSearchResultLayer.removeAll();

                // Use first result in the list
                LocatorGeocodeResult geocodeResult = result.get(0);

                // get return geometry from geocode result
                Point resultPoint = geocodeResult.getLocation();
                // create marker symbol to represent location
                SimpleMarkerSymbol resultSymbol = new SimpleMarkerSymbol
                        (Color.RED, 16, SimpleMarkerSymbol.STYLE.CIRCLE);
                // create graphic object for resulting location
                Graphic resultLocGraphic = new Graphic(resultPoint, resultSymbol);
                // add graphic to location layer
                mSearchResultLayer.addGraphic(resultLocGraphic);

                // create text symbol for return addressList
                String address = geocodeResult.getAddress();
                TextSymbol resultAddress = new TextSymbol(20, address, Color.BLACK);
                // create offset for text
                resultAddress.setOffsetX(-4 * address.length());
                resultAddress.setOffsetY(10);
                // create a graphic object for addressList text
                Graphic resultText = new Graphic(resultPoint, resultAddress);
                // add addressList text graphic to location graphics layer
                mSearchResultLayer.addGraphic(resultText);

                mSearchResultLayerPoint = resultPoint;

                // Zoom map to geocode result location
                mMapView.zoomToResolution(geocodeResult.getLocation(), 2);
            }
        }
    }

    public void onRouteClicked(){

        if(!allAddressLoadedInWGS84){
            for(int i = 0; i < mLocationLayerPoint.size(); i++){
                allPointsForThread.add(  (Point)
                        GeometryEngine.project(mLocationLayerPoint.get(i), wm, egs)  );
                allAddressLoadedInWGS84 = true;
            }
        }


        clearAll();

        /*
        // Adding the symbol for start point
        SimpleMarkerSymbol startSymbol = new SimpleMarkerSymbol(Color.DKGRAY,
                15, SimpleMarkerSymbol.STYLE.CIRCLE);
        Graphic gStart = new Graphic(mLocationLayerPoint.get(0), startSymbol);
        routeLayer.addGraphic(gStart);
        Log.d("route","routeLayer");
        */
        QueryDirections(allPointsForThread);
        Log.d("route","Query");

    }

    private void QueryDirections(final List<Point> point) {
        // Show that the route is calculating
        dialog = ProgressDialog.show(SendActivity.this, "路径",
                "计算路径中...", true);
        // Spawn the request off in a new thread to keep UI responsive
        Thread t = new Thread() {
            @Override
            public void run() {

                try {
                    RouteParameters rp = null;
                    NAFeaturesAsFeature rfaf = null;
                    StopGraphic point1 = null;
                    StopGraphic point2 = null;

                    int routeNum = 0;

                    for (int i = 0; i + 1 < point.size(); i++) {
                        for(int j = i + 1; j < point.size(); j++){
                            //singleRouteInit()中只是创建了类的数组，
                            //并没有把类实例化，所以在用new方法实例化之前
                            //不能对类中的对象赋值
                            //记录这条路径的起止点
                            allSingleRoute[routeNum++] = new ExpressDistance(i, j);
                            // Start building up routing parameters
                            rp = mRouteTask
                                    .retrieveDefaultRouteTaskParameters();
                            rfaf = new NAFeaturesAsFeature();
                            // Convert point to EGS (decimal degrees)
                            // Create the stop points (start at our location, go
                            // to pressed location)

                            point1 = new StopGraphic(point.get(i));
                            point2 = new StopGraphic(point.get(j));

                            rfaf.setFeatures(new Graphic[]{point1, point2});
                            rfaf.setCompressedRequest(true);

                            rp.setStops(rfaf);

                            // Set the routing service output SR to our map
                            // service's SR
                            //rp.setOutSpatialReference(wm);
                            rp.setOutSpatialReference(mMapView.getSpatialReference());

                            // Solve the route and use the results to update UI
                            // when received
                            mResults.add(mRouteTask.solve(rp));
                        }
                    }
                    if (mResults == null) {
                        Toast.makeText(SendActivity.this, mException.toString(),
                                Toast.LENGTH_LONG).show();
                        curDirections = null;
                        return;
                    }

                    //record the number of routing directions
                    int numOfRoutingDirections = 0;

                    int num = mResults.size();

                    for(int i = 0; i < num; i++) {
                        curRoute = mResults.get(i).getRoutes().get(0);
                        numOfRoutingDirections += mResults.get(i).getRoutes().get(0).getRoutingDirections().size();

                        //顺序记录路径长度
                        allSingleRoute[i].dis = curRoute.getTotalMiles();
                    }

                    selectedSegmentID = -1;
                    try{
                        shortestRoute = new AntColonyAlgorithm().AntsMethod
                                (singleAddressNum, allSingleRoute, 0, 2 * addressList.size());
                    }
                    catch (Exception e) {
                        mException = e;
                        Toast.makeText(SendActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                    for(int i = 0; i < allPointsForThread.size(); i++){
                        shortestRoutePoints.add(allPointsForThread.get(shortestRoute[i])  );
                    }//correct!

                    //清空用于计算所有两点间路径的mResults
                    mResults = new ArrayList<RouteResult>();

                    for(int i = 0; i + 1 < shortestRoutePoints.size(); i++){
                        // Start building up routing parameters
                        rp = mRouteTask
                                .retrieveDefaultRouteTaskParameters();
                        rfaf = new NAFeaturesAsFeature();
                        // Convert point to EGS (decimal degrees)
                        // Create the stop points (start at our location, go to pressed location)
                        point1 = new StopGraphic(shortestRoutePoints.get(i));
                        point2 = new StopGraphic(shortestRoutePoints.get(i + 1));

                        rfaf.setFeatures(new Graphic[]{point1, point2});
                        rfaf.setCompressedRequest(true);

                        rp.setStops(rfaf);

                        // Set the routing service output SR to our map
                        // service's SR
                        //rp.setOutSpatialReference(wm);
                        rp.setOutSpatialReference(mMapView.getSpatialReference());
                        mResults.add(mRouteTask.solve(rp));
                    }

                    //连接首位两点
                    if(mIsMapLoaded){
                        // Start building up routing parameters
                        rp = mRouteTask
                                .retrieveDefaultRouteTaskParameters();
                        rfaf = new NAFeaturesAsFeature();
                        // Convert point to EGS (decimal degrees)
                        // Create the stop points (start at our location, go
                        // to pressed location)

                        point1 = new StopGraphic(shortestRoutePoints.
                                get(shortestRoutePoints.size() - 1));
                        point2 = new StopGraphic(shortestRoutePoints.get(0));

                        rfaf.setFeatures(new Graphic[]{point1, point2});
                        rfaf.setCompressedRequest(true);

                        rp.setStops(rfaf);

                        // Set the routing service output SR to our map
                        // service's SR
                        //rp.setOutSpatialReference(wm);
                        rp.setOutSpatialReference(mMapView.getSpatialReference());

                        // Solve the route and use the results to update UI
                        // when received
                        mResults.add(mRouteTask.solve(rp));
                    }


                    mHandler.post(mUpdateResults);
                } catch (Exception e) {
                    mException = e;
                    mHandler.post(mUpdateResults);
                }
            }

        };
        // Start the operation
        t.start();

    }

    /**
     * Updates the UI after a successful rest response has been received.
     */
    void updateUI() {
        dialog.dismiss();

        if (mResults == null) {
            Toast.makeText(SendActivity.this, mException.toString(),
                    Toast.LENGTH_LONG).show();
            curDirections = null;
            return;
        }

        // Creating a fragment if it has not been created
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag("Nav Drawer") == null) {
            FragmentTransaction ft = fm.beginTransaction();
            RoutingListFragment frag = new RoutingListFragment();
            ft.add(frag, "Nav Drawer");
            ft.commit();
        } else {
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fm.findFragmentByTag("Nav Drawer"));
            RoutingListFragment frag = new RoutingListFragment();
            ft.add(frag, "Nav Drawer");
            ft.commit();
        }

        // Unlock the NAvigation Drawer
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //record the number of routing directions
        int numOfRoutingDirections = 0;

        int num = mResults.size();

        for(int i = 0; i < num; i++){
            curRoute = mResults.get(i).getRoutes().get(0);
            numOfRoutingDirections += mResults.get(i).getRoutes().get(0).getRoutingDirections().size();
            // Symbols for the route and the destination (blue line, checker flag)
            SimpleLineSymbol routeSymbol = new SimpleLineSymbol(Color.BLUE, 3);
            PictureMarkerSymbol destinationSymbol = new PictureMarkerSymbol(
                    mMapView.getContext(), getResources().getDrawable(
                    R.drawable.ic_destination));

            /*
            //顺序记录路径长度
            allSingleRoute[i].dis = curRoute.getTotalMiles();
            */



            // Add all the route segments with their relevant information to the
            // hiddenSegmentsLayer, and add the direction information to the list
            // of directions
            for (RouteDirection rd : curRoute.getRoutingDirections()) {
                HashMap<String, Object> attribs = new HashMap<>();
                attribs.put("text", rd.getText());
                attribs.put("time", Double.valueOf(rd.getMinutes()));
                attribs.put("length", Double.valueOf(rd.getLength()));
                curDirections.add(String.format("%s%n%.1f minutes (%.1f miles)",
                        rd.getText(), rd.getMinutes(), rd.getLength()));
                Graphic routeGraphic = new Graphic(rd.getGeometry(), segmentHider,
                        attribs);
                hiddenSegmentsLayer.addGraphic(routeGraphic);
            }
            // Reset the selected segment
            selectedSegmentID = -1;

            // Add the full route graphics, start and destination graphic to the
            // routeLayer
            Graphic routeGraphic = new Graphic(curRoute.getRouteGraphic()
                    .getGeometry(), routeSymbol);
            Graphic endGraphic = new Graphic(
                    ((Polyline) routeGraphic.getGeometry()).getPoint(((Polyline) routeGraphic
                            .getGeometry()).getPointCount() - 1), destinationSymbol);
            routeLayer.addGraphics(new Graphic[] { routeGraphic, endGraphic });
            //routeLayer.addGraphics(new Graphic[] { routeGraphic });

            totalTime += curRoute.getTotalMinutes();
            totalLength += curRoute.getTotalMiles();

            // Get the full route summary and set it as our current label
            routeSummary = String.format("%s%n%.1f minutes (%.1f miles)",
                    "Total Routes", totalTime, totalLength);

            directionsLabel.setText(routeSummary);

            // Get the full route summary and set it as our current label
            routeSummary = String.format("%s%n%.1f minutes (%.1f miles)",
                    curRoute.getRouteName(), curRoute.getTotalMinutes(),
                    curRoute.getTotalMiles());

            directionsLabel.setText(routeSummary);



            // Zoom to the extent of the entire route with a padding
            //mMapView.setExtent(curRoute.getEnvelope(), 250);
            mMapView.setExtent(allAddressExtent, 250);

            //Start Point of Each Route
            if(i == 0){
                // Adding the symbol for start point
                SimpleMarkerSymbol startSymbol = new SimpleMarkerSymbol(Color.DKGRAY,
                        15, SimpleMarkerSymbol.STYLE.CIRCLE);
                Graphic gStart = new Graphic(
                        mLocationLayerPoint.get(shortestRoute[0]), startSymbol);
                routeLayer.addGraphic(gStart);
                Log.d("route","routeLayer");

                HashMap<String, Object> attribs = new HashMap<>();
                attribs.put("text", addressList.get(shortestRoute[0]) );

                Graphic attribsStartGraphic = new Graphic(gStart.getGeometry(), segmentHider,
                        attribs);
                hiddenSegmentsLayer.addGraphic(attribsStartGraphic);

                // Replacing the first and last direction segments
                curDirections.remove(0);
                curDirections.add(0, addressList.get(shortestRoute[0]));
            }
            else if(i > 0){
                curDirections.remove(curDirections.size() -
                        mResults.get(i).getRoutes().get(0).getRoutingDirections().size());
            }

            //End Point of Each Route
            curDirections.remove(curDirections.size() - 1);

            HashMap<String, Object> attribs = new HashMap<>();
            if(i + 1 < num){
                attribs.put("text", addressList.get(shortestRoute[i + 1]) );
                curDirections.add( addressList.get(shortestRoute[i + 1]) );
            }
            else{//回到起点
                attribs.put("text", addressList.get(shortestRoute[0]) );
                curDirections.add( addressList.get(shortestRoute[0]) );
            }
            Graphic attribsEndGraphic = new Graphic(endGraphic.getGeometry(), segmentHider,
                    attribs);
            hiddenSegmentsLayer.addGraphic(attribsEndGraphic);
        }

    }

    private void setupLocator() {
        // Parameterless constructor - uses the Esri world geocoding service.
        mLocator = Locator.createOnlineLocator();
    }

    private void setupLocationListener() {
        if ((mMapView != null) && (mMapView.isLoaded())) {
            mLDM = mMapView.getLocationDisplayManager();
            mLDM.setLocationListener(new LocationListener() {

                boolean locationChanged = false;

                // Zooms to the current location when first GPS fix arrives.
                @Override
                public void onLocationChanged(Location loc) {
                    if (!locationChanged) {
                        locationChanged = true;
                        zoomToLocation(loc);

                        // After zooming, turn on the Location pan mode to show the location
                        // symbol. This will disable as soon as you interact with the map.
                        mLDM.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION);
                    }
                }

                @Override
                public void onProviderDisabled(String arg0) {
                }

                @Override
                public void onProviderEnabled(String arg0) {
                }

                @Override
                public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                }
            });

            mLDM.start();
        }
    }

    /**
     * Zoom to location using a specific size of extent.
     *
     * @param loc  the location to center the MapView at
     */
    private void zoomToLocation(Location loc) {
        Point mapPoint = getAsPoint(loc);
        Unit mapUnit = mMapSr.getUnit();
        double zoomFactor = Unit.convertUnits(ZOOM_BY,
                Unit.create(LinearUnit.Code.MILE_US), mapUnit);
        Envelope zoomExtent = new Envelope(mapPoint, zoomFactor, zoomFactor);
        mMapView.setExtent(zoomExtent);
    }

    private Point getAsPoint(Location loc) {
        Point wgsPoint = new Point(loc.getLongitude(), loc.getLatitude());
        return (Point) GeometryEngine.project(wgsPoint, SpatialReference.create(4326),
                mMapSr);
    }

    private void executeLocatorTask(List<String> address) {

        //clear place search result
        mSearchResultLayer.removeAll();

        LocatorFindParameters[] allFindParams = new LocatorFindParameters[address.size()];
        for(int i = 0; i < address.size(); i++){

            // Create Locator parameters from single line addressList string
            //LocatorFindParameters findParams = new LocatorFindParameters(addressList);
            LocatorFindParameters findParams = new LocatorFindParameters("");


            // Use the centre of the current map extent as the find location point
            findParams.setLocation(mMapView.getCenter(), mMapView.getSpatialReference());

            // Calculate distance for find operation
            Envelope mapExtent = new Envelope();
            mMapView.getExtent().queryEnvelope(mapExtent);
            // assume map is in metres, other units wont work, double current envelope
            double distance = (mapExtent != null && mapExtent.getWidth() > 0) ? mapExtent.getWidth() * 2 : 10000;
            findParams.setDistance(distance);
            findParams.setMaxLocations(2);

            // Set addressList spatial reference to match map
            findParams.setOutSR(mMapView.getSpatialReference());

            findParams.setText(address.get(i));

            allFindParams[i] = findParams;

        }

        new LocatorAsyncTask().execute(allFindParams);
    }

    private class LocatorAsyncTask extends AsyncTask<LocatorFindParameters, Void, List<LocatorGeocodeResult>> {
        private Exception mException;

        @Override
        protected List<LocatorGeocodeResult> doInBackground(LocatorFindParameters ... params) {
            mException = null;
            List<LocatorGeocodeResult> results = new ArrayList<LocatorGeocodeResult>();
            Locator locator = Locator.createOnlineLocator();//Locator
            try {
                for(int i = 0; i < params.length; i++){
                    results.add( locator.find(params[i]).get(0) );
                }
            } catch (Exception e) {
                mException = e;
            }
            return results;
        }
        protected void onPostExecute(List<LocatorGeocodeResult> result) {
            if (mException != null) {
                Log.w("PlaceSearch", "LocatorSyncTask failed with:");
                mException.printStackTrace();
                Toast.makeText(SendActivity.this,"地址搜索异常", Toast.LENGTH_LONG).show();
                return;
            }

            if (result.size() == 0) {
                Toast.makeText(SendActivity.this,"没有找到对应地址", Toast.LENGTH_LONG).show();
            } else {

                //Copy all the geocode results
                allGeocodeResults = result;
                int num = addressList.size();
                double xMin = 0, yMin = 0, xMax = 0, yMax = 0;
                for(int i = 0; i < result.size(); i++){
                    Point tempPoint = result.get(i).getLocation();
                    if(i == 0){
                        xMin = xMax = tempPoint.getX();
                        yMin = yMax = tempPoint.getY();
                    }
                    else{
                        if(tempPoint.getX() < xMin){
                            xMin = tempPoint.getX();
                        }
                        else if(tempPoint.getX() > xMax){
                            xMax = tempPoint.getX();
                        }
                        else if(tempPoint.getY() < yMin){
                            yMin = tempPoint.getY();
                        }
                        else if(tempPoint.getY() > yMax){
                            yMax = tempPoint.getY();
                        }
                    }
                }

                for(int i = 0; i < num; i++){
                    // Use first result in the list
                    LocatorGeocodeResult geocodeResult = result.get(i);
                    // get return geometry from geocode result
                    Point resultPoint = geocodeResult.getLocation();

                    // create marker symbol to represent location
                    SimpleMarkerSymbol resultSymbol =
                            new SimpleMarkerSymbol(Color.RED, 16, SimpleMarkerSymbol.STYLE.CIRCLE);

                    // add addressList attributes
                    HashMap<String, Object> attribs = new HashMap<>();
                    attribs.put("text", geocodeResult.getAddress());
                    // create graphic object for resulting location
                    Graphic resultLocGraphic = new Graphic(resultPoint, resultSymbol,
                            attribs);

                    // add graphic to location layer
                    mLocationLayer.addGraphic(resultLocGraphic);

                    /*
                    // create text symbol for return addressList
                    String addressList = geocodeResult.getAddress();
                    TextSymbol resultAddress = new TextSymbol(20, addressList, Color.BLACK);
                    // create offset for text
                    resultAddress.setOffsetX(-4 * addressList.length());
                    resultAddress.setOffsetY(10);
                    // create a graphic object for addressList text
                    Graphic resultText = new Graphic(resultPoint, resultAddress);
                    // add addressList text graphic to location graphics layer
                    mLocationLayer.addGraphic(resultText);
                    */

                    mLocationLayerPoint.add(resultPoint);
                }

                // Zoom map to the last geocode result location
                allAddressExtent = new Envelope(xMin, yMin, xMax, yMax);
                mMapView.setExtent(allAddressExtent, 250);
            }
        }
    }

    public void clearAll() {

        singleRouteInit();//clear allSingleRoute
        shortestRoute = null;
        shortestRoutePoints = new ArrayList<Point>();

        //Removing the graphics from the layer
        routeLayer.removeAll();
        hiddenSegmentsLayer.removeAll();
        mSearchResultLayer.removeAll();

        curDirections = new ArrayList<>();
        mResults = new ArrayList<RouteResult>();
        curRoute = null;

        //Setting to default text
        directionsLabel.setText("");

        //Locking the Drawer
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //Removing the RoutingListFragment if present
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag("Nav Drawer") != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fm.findFragmentByTag("Nav Drawer"));
            ft.commit();
        }

    }

    private void initToolbar()
    {
        toolbar.setTitle("导航");
        toolbar.setTitleTextColor(getResources().getColor(R.color.windowBackground));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.direction:
                        if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {//原来是Gravity.END
                            mDrawerLayout.closeDrawers();
                        } else {
                            mDrawerLayout.openDrawer(GravityCompat.END);//原来是Gravity.END
                        }
                        break;
                    case R.id.action_load:
                        executeLocatorTask(addressList);
                        mIsAddressLoaded = true;
                        break;
                }
                return true;
            }
          };
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_activity_layout);
        addressInit();
        singleAddressNumInit();
        singleRouteInit();

        mMapView = (MapView)findViewById(R.id.send_activity_layout_mapview);
        mMapView.setOnStatusChangedListener(statusChangedListener);

        setupLocator();
        setupLocationListener();

        mSearchResultLayer = new GraphicsLayer();
        mMapView.addLayer(mSearchResultLayer);

        //mLocationLayer is under routeLayer
        mLocationLayer = new GraphicsLayer();
        mMapView.addLayer(mLocationLayer);

        // Add the route graphic layer (shows the full route)
        routeLayer = new GraphicsLayer();
        mMapView.addLayer(routeLayer);

        // Add the hidden segments layer (for highlighting route segments)
        hiddenSegmentsLayer = new GraphicsLayer();
        mMapView.addLayer(hiddenSegmentsLayer);

        // Make the segmentHider symbol "invisible"
        segmentHider.setAlpha(1);

        // Initialize the RouteTask
        try {
            mRouteTask = RouteTask
                    .createOnlineRouteTask(
                            "http://sampleserver3.arcgisonline.com/ArcGIS/rest/services/Network/USA/NAServer/Route",
                            null);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }

        // Make the segmentHider symbol "invisible"
        segmentHider.setAlpha(1);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.send_drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        calculateCardView = (CardView) findViewById(R.id.send_activity_layout_calculateroute);
        getDirectionCardView = (CardView) findViewById(R.id.send_activity_layout_gps);
        nextButton=(AppCompatButton)findViewById(R.id.send_activity_layout_nextbutton);
        belowButton=(AppCompatButton)findViewById(R.id.send_activity_layout_bellowbutton);
        directionsLabel = (TextView) findViewById(R.id.send_activity_layout_directionsLabel);
        toolbar=(Toolbar)findViewById(R.id.Toolbar);
        initToolbar();

        belowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String segment = directionsLabel.getText().toString();
                ListView lv = RoutingListFragment.mDrawerList;
                if(lv!=null){
                    for (int i = 0; i < lv.getCount(); i++)
                    {
                        String lv_segment = lv.getItemAtPosition(i).toString();
                        if (segment.equals(lv_segment))
                        {
                            if(i==0) i++;
                            ((MyAdapter)lv.getAdapter()).setSelectedItemID(i-1);
                            directionsLabel.setText(lv.getItemAtPosition(i-1).toString());
                            onSegmentSelected(lv.getItemAtPosition(i-1).toString());
                            lv.setSelection(i-1);
                            ((MyAdapter)lv.getAdapter()).notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String segment = directionsLabel.getText().toString();
                ListView lv = RoutingListFragment.mDrawerList;
                if(lv!=null){
                    for (int i = 0; i < lv.getCount(); i++)
                    {
                        String lv_segment = lv.getItemAtPosition(i).toString();
                        if (segment.equals(lv_segment))
                        {
                            if(i==lv.getCount()-1) i--;
                            ((MyAdapter)lv.getAdapter()).setSelectedItemID(i+1);
                            directionsLabel.setText(lv.getItemAtPosition(i+1).toString());
                            onSegmentSelected(lv.getItemAtPosition(i+1).toString());
                            lv.setSelection(i+1);
                            ((MyAdapter)lv.getAdapter()).notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        directionsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curDirections != null&&RoutingListFragment.mDrawerList!=null){
                    mDrawerLayout.openDrawer(GravityCompat.END);//原来是Gravity.END
                    String segment = directionsLabel.getText().toString();
                    ListView lv = RoutingListFragment.mDrawerList;
                    for (int i = 0; i < lv.getCount(); i++)
                    {
                        String lv_segment = lv.getItemAtPosition(i).toString();
                        if (segment.equals(lv_segment))
                        {
                            ((MyAdapter)lv.getAdapter()).setSelectedItemID(i);
                            lv.setSelection(i);
                            ((MyAdapter)lv.getAdapter()).notifyDataSetChanged();
                        }
                    }
                }
            }
        });


        getDirectionCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMapView.isLoaded()) {
                    if ((mLDM != null) && (mLDM.getLocation() != null)) {
                        mLDM.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION);
                    }
                }
            }
        });

        calculateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mIsAddressLoaded){
                    Snackbar.make(getDirectionCardView.getRootView(),"缺少货物数据",Snackbar.LENGTH_LONG).show();
                }
                else{
                    onRouteClicked();
                }
            }
        });


        // Get the location display manager and start reading location. Don't
        // auto-pan
        // to center our position
        /*
        ldm = map.getLocationDisplayManager();
        ldm.setLocationListener(new MyLocationListener());
        ldm.start();
        ldm.setAutoPanMode(LocationDisplayManager.AutoPanMode.OFF);
        */

        /**
         * On single tapping the map,
         * 1 query fot a addressList point and show addressList
         * in the label
         * 2 query for a route segment and highlight
         * the segment and show direction summary in the label if a segment is
         * found.
         */
        mMapView.setOnSingleTapListener(new OnSingleTapListener() {
            private static final long serialVersionUID = 1L;

            public void onSingleTap(float x, float y) {

                // Get all the graphics within 20 pixels the click
                int[] indexes = mLocationLayer.getGraphicIDs(x, y, 20);
                if(indexes.length >= 1) {
                    // Update our currently selected segment
                    selectedSegmentID = indexes[0];
                    Graphic selected = mLocationLayer
                            .getGraphic(selectedSegmentID);
                    // Highlight it on the map
                    //hiddenSegmentsLayer.updateGraphic(selectedSegmentID, segmentShower);
                    if (selected.getGeometry().getType() == Geometry.Type.POINT) {
                        // Update the label with this direction's information
                        String label = ((String) selected.getAttributeValue("text"));
                        directionsLabel.setText(label);

                        Point selectedPoint = (Point)selected.getGeometry();
                        // Zoom map to the selected point
                        mMapView.zoomToResolution(selectedPoint, 2);

                        return;
                    }
                }
                indexes = hiddenSegmentsLayer.getGraphicIDs(x, y, 20);
                // Hide the currently selected segment
                hiddenSegmentsLayer.updateGraphic(selectedSegmentID,
                        segmentHider);

                if (indexes.length < 1) {
                    // If no segments were found but there is currently a route,
                    // zoom to the extent of the full route
                    if (curRoute != null) {
                        //mMapView.setExtent(curRoute.getEnvelope(), 250);
                        mMapView.setExtent(allAddressExtent, 250);
                        directionsLabel.setText(routeSummary);
                    }
                    return;
                }
                // Otherwise update our currently selected segment
                selectedSegmentID = indexes[0];
                Graphic selected = hiddenSegmentsLayer
                        .getGraphic(selectedSegmentID);
                // Highlight it on the map
                hiddenSegmentsLayer.updateGraphic(selectedSegmentID,
                        segmentShower);
                String direction = ((String) selected.getAttributeValue("text"));
                double time = (Double) selected.getAttributeValue("time");
                double length = (Double) selected.getAttributeValue("length");
                // Update the label with this direction's information
                String label = String.format("%s%n%.1f minutes (%.1f miles)",
                        direction, time, length);
                directionsLabel.setText(label);
                // Zoom to the extent of that segment
                mMapView.setExtent(selected.getGeometry(), 50);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_send, menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);

        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("地址");
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Snackbar.make(getDirectionCardView.getRootView(),"返回",Snackbar.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        executePlaceSearchTask(query);
        Toast.makeText(SendActivity.this, "Click Search", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
        if (mLDM != null) {
            mLDM.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.unpause();
        if (mLDM != null) {
            mLDM.resume();
        }
    }

    /*
	 * When the user selects the segment from the listview, it gets highlighted
	 * on the map
	 */
    @Override
    public void onSegmentSelected(String segment) {

        if (segment == null)
            return;
        // Look for the graphic that corresponds to this direction
        for (int index : hiddenSegmentsLayer.getGraphicIDs()) {
            Graphic g = hiddenSegmentsLayer.getGraphic(index);
            if (segment.contains((String) g.getAttributeValue("text"))) {
                // When found, hide the currently selected, show the new
                // selection
                hiddenSegmentsLayer.updateGraphic(selectedSegmentID,
                        segmentHider);
                hiddenSegmentsLayer.updateGraphic(index, segmentShower);
                selectedSegmentID = index;
                // Update label with information for that direction
                directionsLabel.setText(segment);
                Geometry.Type type = hiddenSegmentsLayer.getGraphic(selectedSegmentID)
                        .getGeometry().getType();
                // Zoom to the extent of that segment
                if(type == Geometry.Type.POLYLINE){
                    mMapView.setExtent(hiddenSegmentsLayer.getGraphic(selectedSegmentID)
                            .getGeometry(), 50);
                }
                else if(type == Geometry.Type.POINT){
                    Point selected = (Point)hiddenSegmentsLayer.getGraphic(selectedSegmentID)
                            .getGeometry();
                    // Zoom map to the selected point
                    mMapView.zoomToResolution(selected, 2);
                }
                break;
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLDM != null) {
            mLDM.stop();
        }
    }

}
