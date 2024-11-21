
# NETWORK SCANN APP

## Overview
The Network Scan App is designed to provide users with detailed information about available cellular, Bluetooth, and Wi-Fi networks. The app allows users to scan for networks, view their details, and export the information to CSV files for further analysis.

## Built With

- Kotlin (JAVA)
- Android Studio

## Features
1. Cellular Network Scanning
Cellular Network Fragment: Displays information about available cellular networks.
- Data Displayed:
Network Type (e.g., `WCDMA`, `LTE`, `GSM`, `CDMA`)
- Operator Name
Mobile Country Code (MCC) and Mobile Network Code (MNC)
- Location Area Code (LAC) and Cell Identifier (CID)
- Signal Strength (in dBm)
- Frequency (UARFCN or EARFCN)
Permissions Required:
`ACCESS_FINE_LOCATION`
`ACCESS_COARSE_LOCATION`

2. Bluetooth Device Scanning
Bluetooth Devices Fragment: Lists all discoverable Bluetooth devices.
Data Displayed:
- Device Name
- Device Address (MAC)
Export Functionality: Users can export the list of discovered devices to a CSV file.
Permissions Required:
`BLUETOOTH`
`BLUETOOTH_ADMIN`
`BLUETOOTH_SCAN`
`BLUETOOTH_CONNECT`

3. Wi-Fi Network Scanning
Wi-Fi Fragment: Displays available Wi-Fi networks.
Data Displayed:
- `SSID` (Network Name)
- Frequency (in GHz)
- Channel
- Signal Level (in dBm)
Refresh Functionality: Users can refresh the list of available Wi-Fi networks.
Permissions Required:
`ACCESS_FINE_LOCATION`
`CHANGE_WIFI_STATE`
`ACCESS_WIFI_STATE`

4. Combined Data View [archived]
Combined Data Fragment: Aggregates data from cellular, Bluetooth, and Wi-Fi scans.
Data Displayed: Lists all collected data from the three categories.
Export Functionality: Users can export the combined data to a CSV file.

5. User Interface
Navigation: The app features a bottom navigation bar for easy access to different sections (Bluetooth, Wi-Fi, Cellular).
Recycler Views: Utilizes RecyclerViews to display lists of networks and devices efficiently.
Dynamic Updates: The UI updates dynamically as new data is fetched or when the user interacts with the app.

6. Permissions Handling
The app checks for necessary permissions at runtime and requests them if not granted.
Handles permission denial gracefully, informing users of the need for permissions to access certain features.

7. Data Exporting
Users can export data from only Bluetooth scans to CSV files.
The app uses FileProvider to share the exported files securely.

8. Theming and Layout
The app supports both light and dark themes, providing a consistent user experience across different device settings.
Utilizes ConstraintLayout for flexible and responsive UI design.

Conclusion

The Network Scan App is a comprehensive tool for users to gather and analyze network information from their surroundings. With its user-friendly interface and robust features, it serves as an essential application for network analysis and management.

## CELULAR:
### Exemples
- Type de réseau : WCDMA
- Opérateur : Airtel
- MCC/MNC : 630/02 
- Localisation :
- LAC : 7201
- CID : 196817775
- Signal : -120 dBm
- Fréquence : UARFCN = 2962

### Analyse des champs
- mLac=7201 :
LAC (Location Area Code) : Identifiant unique de la zone où se trouve la cellule.

- mCid=196817775 :
CID (Cell Identifier) : Identifiant unique de la cellule au sein de la LAC.

- mPsc=442 :
PSC (Primary Scrambling Code) : Code utilisé pour identifier la cellule dans les réseaux WCDMA.

- mUarfcn=2962 :
UARFCN (UTRA Absolute Radio Frequency Channel Number) : Indique la fréquence de fonctionnement de la cellule.

- mMcc=630 :
MCC (Mobile Country Code) : Code de pays (ici, 630 correspond à la République Démocratique du Congo).

- mMnc=02 :
MNC (Mobile Network Code) : Code de réseau mobile (02 correspond à Airtel dans cet exemple).

- mAlphaLong=Airtel, mAlphaShort=Airtel :
Noms longs et courts de l'opérateur.

- Signal Strength: -120 dBm :
La force du signal, exprimée en décibels milliwatt (dBm). Un signal à -120 dBm est très faible.

`! Some devices or carriers restrict access to information about neighboring cells for privacy and security reasons. This limitation is often imposed by the operating system or network operator.`

## BLUETOOTH
List les noms et les address

## WIFI
List les noms, frequences, cannaux, et niveau
