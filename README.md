# BT-Remote
一个Android端的蓝牙遥控APP，提供Arduino库，方便用于对机器人、小车等做无线遥控和调试等用途。



### 通信格式

```java
public final static String getPIDValues = "GP;";
public final static String getSettings = "GS;";
public final static String getInfo = "GI;";
public final static String getKalman = "GK;";

public final static String setPValue = "SP,";
public final static String setIValue = "SI,";
public final static String setDValue = "SD,";
public final static String setKalman = "SK,";
public final static String setTargetAngle = "ST,";
public final static String setMaxAngle = "SA,";
public final static String setMaxTurning = "SU,";
public final static String setBackToSpot = "SB,";

public final static String imuBegin = "IB;";
public final static String imuStop = "IS;";

public final static String statusBegin = "RB;";
public final static String statusStop = "RS;";

public final static String sendStop = "CS;";
public final static String sendIMUValues = "CM,";
public final static String sendJoystickValues = "CJ,";
public final static String sendPairWithWii = "CPW;";
public final static String sendPairWithPS4 = "CPP;";

public final static String restoreDefaultValues = "CR;";

public final static String responsePIDValues = "P";
public final static String responseKalmanValues = "K";
public final static String responseSettings = "S";
public final static String responseInfo = "I";
public final static String responseIMU = "V";
public final static String responseStatus = "R";
public final static String responsePairConfirmation = "PC";

public final static int responsePIDValuesLength = 5;
public final static int responseKalmanValuesLength = 4;
public final static int responseSettingsLength = 4;
public final static int responseInfoLength = 4;
public final static int responseIMULength = 4;
public final static int responseStatusLength = 3;
public final static int responsePairConfirmationLength = 1;

public final static String sendClickerButton = "AJ,";
public final static int clickerUp = 1;
public final static int clickerLeft = 2;
public final static int clickerRight = 3;
public final static int clickerStop = 0;
public final static int clickerSwitch = 4;

public final static String sendCommand = "CD,";

```

### 自动探测模式指令格式

发送指令格式：`CD,(command);`

command 所有取值: `left`, `right`, `move`, `detect`

#### 左转90度

app向智能车发送指令：`CD,left;`

智能车左转90度完成后发回：`done`

#### 右转90度

app向智能车发送指令：`CD,right;`

智能车右转90度完成后发回：`done`

#### 前进1格

app向智能车发送指令：`CD,move;`

智能车前进1格完成后发回：`done`

#### 超声波探测

app向智能车发送指令：`CD,detect;`

智能车探测完成后发回：`(dist)done`

dist为探测得到小车前方最近障碍物距离小车的格数

例如若小车在(0, 0)格向x轴正方向探测，发现(1, 0)是障碍物，则向app发回`1done`
