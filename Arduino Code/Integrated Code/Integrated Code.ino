//libraries
#include "DHT.h"

//pin configuration
//Analog pin
#define DHTPIN A0

int PIN_FAN_LOW;
int PIN_FAN_MED;
int PIN_FAN_HIGH;

//Analog pins
int PIN_LEVEL_LOW;
int PIN_LEVEL_MED;
int PIN_LEVEL_HIGH;

int PIN_SOLENOID;

int PIN_BT_RX;
int PIN_BT_TX;
int PIN_PUMP;

//DEFINE
#define DHTTYPE DHT11

//  Constants
int TEMP_MAX=47;// IN CELCIUS
int TEMP_MIN=20;
int TEMP_DELTA=1;

int HUMD_MAX;
int HUMD_MIN;
int HUMD_DELTA=1;

int WATER_THRESHOLD;

//variables
bool automatic_humidity=true;
bool automatic_temp=true;

char inputByte='z';


DHT dht(DHTPIN, DHTTYPE);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  dht.begin();
}
void loop(){

  if(Serial.available()>0)  
  {
    inputByte=Serial.read();
    
  }
//Automatic
  readDHT();
   
  checkWaterLevel();  
  
}



void checkWaterLevel(){
  
}

//Read DHT11
void readDHT(){
  float h=readHumidity();
  float t= readTemperature();

  if(isnan(h) || isnan(t) ){
    Serial.println("Failed to read DHT11 sensor");
    return;
  }  
  
  Serial.print("Humidity: ");
  Serial.println(h);
  if(automatic_humidity){
    checkHumditity(h);
  }
  
  Serial.print("Temperature: ");
  Serial.println(t);
  if(automatic_temp){
    checkTemperature(t);  
  }
  
}

int checkTemperature(int t){
  if(TEMP_MIN > t){
    //fan speed -> LOW
    digitalWrite(PIN_FAN_LOW,HIGH);
    digitalWrite(PIN_FAN_MED,LOW);
    digitalWrite(PIN_FAN_HIGH,LOW);
    return 0;
  }else if(TEMP_MAX < t){
    //fan speed -> HIGH
    digitalWrite(PIN_FAN_LOW,LOW);
    digitalWrite(PIN_FAN_MED,LOW);
    digitalWrite(PIN_FAN_HIGH,HIGH);
    return 2;
  }else{
    //fan will be on MEDIUM speed;
    digitalWrite(PIN_FAN_LOW,LOW);
    digitalWrite(PIN_FAN_MED,HIGH);
    digitalWrite(PIN_FAN_HIGH,LOW);
    return 1;
  }
}

int checkHumidity(int h){
  if(HUMD_MIN > h)  {
    //pump -> ON
    digitalWrite(PIN_PUMP,HIGH);
    return 1;
  }else if(HUMD_MAX >= h && HUMD_MIN <= h ){
    //pump -> ON
    digitalWrite(PIN_PUMP,HIGH);
    return 1;
  }else{
    //pump -> OFF
    digitalWrite(PIN_PUMP,LOW);
    return 0;
  }
  
}



