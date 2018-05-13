#ifndef _BS_LCD_APP_H_
#define _BS_LCD_APP_H_

#include "SampleApp.h"
#include "LCDDevice.h"
#include "LCDAppCmd.h"
#include "SimpleExecuterAppCmd.h"
#include "DHT11Device.h"
#include "pt.h"

static unsigned char temperatureIcon[] U8G_PROGMEM = {0xC0,0x01,0xE0,0x03,0x60,0x1F,0xA0,0x02,0xA0,0x1E,0xA0,0x02,0xA0,0x1E,0xA0,0x02,
0xA0,0x1E,0xB0,0x06,0x98,0x0C,0xCC,0x19,0xEC,0x1B,0xCC,0x19,0x18,0x0C,0xF0,0x07};

static unsigned char humidityIcon[] U8G_PROGMEM = {0x00,0x00,0x80,0x00,0xC0,0x01,0xE0,0x03,0xF0,0x07,0xF8,0x0F,0xDC,0x1F,0xFC,0x1F,
0xEE,0x3F,0xE6,0x3F,0xE6,0x3F,0xE6,0x3F,0xCC,0x1F,0xD8,0x0F,0xE0,0x03,0x00,0x00};

static unsigned char heatIcon[] U8G_PROGMEM = {0x00,0x00,0x90,0x04,0x48,0x02,0x24,0x01,0x24,0x01,0x48,0x02,0x90,0x04,0x20,0x09,
0x20,0x09,0x90,0x04,0x48,0x02,0x48,0x02,0x90,0x04,0x20,0x09,0x00,0x00,0x00,0x00};

static unsigned char dustConcentrationIcon[] U8G_PROGMEM = {0x00,0x18,0xDB,0xFF,0x18,0x3C,0x6E,0x00};

static unsigned char lightIntensityIcon[] U8G_PROGMEM = {0x00,0x5A,0x3C,0x66,0x66,0x3C,0x5A,0x00};

static unsigned char solidHumidityIcon[] U8G_PROGMEM = {0x00,0x66,0x66,0x18,0x18,0x66,0x66,0x00};

static unsigned char socketOffIcon[] U8G_PROGMEM = {0x38,0x0E,0x38,0x0E,0x38,0x0E,0x38,0x0E,0xFC,0x3F,0xDE,0x3B,0xBE,0x3D,0x7E,0x3E,
0xBC,0x3D,0xD8,0x1B,0xF0,0x0F,0xE0,0x03,0xC0,0x03,0xE0,0x03,0xC0,0x03,0x00,0x00};

static unsigned char socketOnIcon[] U8G_PROGMEM = {0x38,0x0E,0x38,0x0E,0x38,0x0E,0x38,0x0E,0xFC,0x3F,0xFE,0x3F,0xFE,0x3F,0xFE,0x3F,
0xFC,0x3F,0xF8,0x1F,0xF0,0x0F,0xE0,0x03,0xC0,0x03,0xE0,0x03,0xC0,0x03,0x00,0x00};

static unsigned char onlineIcon[] U8G_PROGMEM = {0x00,0x00,0x08,0x10,0x04,0x20,0x12,0x48,0xA9,0x95,0xD5,0xAB,0xD5,0xAB,0xD5,0xAB,
0xA9,0x95,0x92,0x49,0x84,0x21,0x88,0x11,0x80,0x01,0x80,0x01,0xE0,0x07,0xE0,0x07};

static unsigned char offlineIcon[] U8G_PROGMEM = {0x00,0x00,0x08,0x60,0x04,0x70,0x12,0x78,0xA9,0x9D,0xD5,0xAF,0xD5,0xAF,0xD5,0xAB,
0xF5,0x95,0xF9,0x49,0xBA,0x21,0x9C,0x11,0x8E,0x01,0x87,0x01,0xE3,0x07,0xE0,0x07
};

static unsigned char dangerIcon[] U8G_PROGMEM = {0x00,0x00,0x80,0x01,0x00,0x00,0x00,0x00,0xC0,0x03,0x00,0x00,0x00,0x00,0xC0,0x03,
0x00,0x00,0x00,0x00,0xE0,0x07,0x00,0x00,0x00,0x00,0xF0,0x0F,0x00,0x00,0x00,0x00,
0xF0,0x0F,0x00,0x00,0x00,0x00,0xF8,0x1F,0x00,0x00,0x00,0x00,0xF8,0x1F,0x00,0x00,
0x00,0x00,0xF8,0x1F,0x00,0x00,0x00,0x00,0x7C,0x3E,0x00,0x00,0x00,0x00,0x7E,0x7E,
0x00,0x00,0x00,0x00,0x3E,0x7C,0x00,0x00,0x00,0x00,0x1F,0xF8,0x00,0x00,0x00,0x00,
0x1F,0xF8,0x00,0x00,0x00,0x00,0xCF,0xF3,0x01,0x00,0x00,0x80,0xCF,0xF3,0x01,0x00,
0x00,0xC0,0xCF,0xF3,0x03,0x00,0x00,0xC0,0xC7,0xE3,0x07,0x00,0x00,0xE0,0xC3,0xC3,
0x07,0x00,0x00,0xE0,0xC3,0xC3,0x07,0x00,0x00,0xE0,0xC1,0x83,0x0F,0x00,0x00,0xF0,
0xC1,0x83,0x0F,0x00,0x00,0xF8,0xC1,0x83,0x1F,0x00,0x00,0xF8,0xC0,0x03,0x3F,0x00,
0x00,0x7C,0xC0,0x03,0x3E,0x00,0x00,0x7C,0xC0,0x03,0x3E,0x00,0x00,0x3C,0xC0,0x03,
0x7C,0x00,0x00,0x3E,0xC0,0x03,0x7C,0x00,0x00,0x3F,0xC0,0x03,0xFC,0x00,0x00,0x1F,
0xC0,0x03,0xF8,0x01,0x80,0x0F,0xC0,0x03,0xF0,0x01,0x80,0x0F,0xC0,0x03,0xF0,0x01,
0x80,0x07,0xC0,0x03,0xE0,0x03,0xC0,0x07,0x80,0x01,0xE0,0x03,0xE0,0x07,0x00,0x00,
0xE0,0x07,0xE0,0x03,0xC0,0x03,0xC0,0x0F,0xF0,0x01,0xC0,0x03,0x80,0x0F,0xF0,0x01,
0xC0,0x03,0x80,0x0F,0xF0,0x00,0xC0,0x03,0x00,0x1F,0xF8,0x00,0xC0,0x03,0x00,0x1F,
0xFC,0x00,0xC0,0x03,0x00,0x3F,0xFC,0x00,0x00,0x00,0x00,0x7F,0xFE,0x00,0x00,0x00,
0x00,0x7F,0xFE,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,
0xFF,0xFF,0xFF,0xFF,0xFE,0xFF,0xFF,0xFF,0xFF,0x7F,0xFC,0xFF,0xFF,0xFF,0xFF,0x3F};

class LCDApp : public SampleApp
{
public:
	LCDApp(LCDDevice& lcd);
	void init();
	void run();
private:
	struct pt ptDanger;
	LCDDevice& lcd;
	float thhIn[3];		//室内温度、湿度、热度
	float thhOut[3];	//室外温度、湿度、热度
	float lightIntensity;	//光照强度
	float dustConcentration;	//PM2.5
	float solidHumidity;	//土壤湿度
	bool isOnline;	//是否在线
	bool isSocket1On;	//插座1是否已经打开
	bool isSocket2On;	//插座2是否已经打开
	bool isSocket3On;	//插座4是否已经打开
	bool isDanger;	//是否危险
	void refreshMainPage();	//刷新主页面
	void printFloat(unsigned char x, unsigned char y, float value);
	void appMsgReceivedCallback(AppMsg& msg);	//应用消息回调
	void refreshDangerAlarm();
	void show();	//显示
	int runDangerAlarmTask();
	void showWelcome();	//显示欢迎页面
};

#endif