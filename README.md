# calibrator-sensor
 [ ![bintray](https://api.bintray.com/packages/xenione/maven/sensor-calibrator/images/download.svg?version=1.0.1) ](https://bintray.com/xenione/maven/sensor-calibrator/1.0.1/link)

Widget to calibrate sensors (Orientation). This Calibrator can be embedded in your own App.

![calibrator](https://cloud.githubusercontent.com/assets/4138527/25778832/1844ba4a-330a-11e7-9d0e-63f85ca63cdf.gif)

App available on [google play](https://play.google.com/store/apps/details?id=com.xenione.apps.calibrator_sensor)

Add it on your project:

Gradle:
```java 
compile 'com.xenione.libs:sensor-calibrator:1.0.1'
```

Add calibrator sensor in your layout.

```java 
 <com.xenione.libs.calibrator.CalibratorView
        android:id="@+id/calibrator"
        android:padding="10dp"
        android:layout_width="match_parent"
        android:layout_height="300dp" />
  ```      
In your code:

```java 
     protected void onCreate(Bundle savedInstanceState) {
        ...
     
        calibratorView = (CalibratorView) findViewById(R.id.calibrator);
        calibratorView.setOnCalibrationListener(new CalibratorView.CalibrationListener() {
            @Override
            public void onCalibrationComplete(int percentage) {
            	// set threshold 70% 
                if (percentage > 70) {
                    // do your staff calibration is done
                }
            }
        });
        // set orientation service
        mOrientationService = new OrientationService(this);
        // register listener.
        mOrientationService.registerUpdateListener(mOrientationListener);
        // sample time, takes 1 sample every 100 ms
        mOrientationService.start(100);
        // add filter if you want to filter signal
        mOrientationService.setFilterX(new FIRFilter());
    }

     // Listeners give you back orientation from device
      private OrientationService.OrientationListener mOrientationListener = new OrientationService.OrientationListener() {
        @Override
        public void onOrientationChanged(float[] orientation) {
        	// pass through calibrator widget azimuth orientation
            calibratorView.setOrientation(orientation[0]);
        }
    };
  ``` 

 # Develop by
-------

Eugeni Josep Senent i Gabriel - @xenione

![linkedin](https://www.linkedin.com/in/eugeni-senent-gabriel-bb198723)

xenione@gmail.com

www.xenione.com


# License
-------
    Copyright 2017 Eugeni Josep Senent i Gabriel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



##Donation
The full money collected here will be destinated to improve Open Source Projects.

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=YLQUW5N9KY9MU)