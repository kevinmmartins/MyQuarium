
#include <Servo.h> 

#define com01 4
#define com02 5

Servo sFiltro;
 
int posAlvo = 0;
int posAtual = 0;
int suav = 125;
int passo = 5;
int motor = 3;
int ciclo = 0;
int vel = 0;

void setup() 
{ 
  sFiltro.attach(9);
  pinMode(motor, OUTPUT);
} 
 
void loop() 
{ 

  ciclo = digitalRead(com01) * 1 + digitalRead(com02) * 2;

  switch (ciclo)
  {
    case 0 :
      digitalWrite (motor, LOW);
      posAlvo = 170;
    break;
    case 1 :
      digitalWrite (motor, HIGH);
      posAlvo = 40;
    break;
    case 2 :
      digitalWrite (motor, HIGH);
      posAlvo = 85;
    break;
    case 3 :
      digitalWrite (motor, HIGH);
      posAlvo = 85;
    break;
  }

  if (posAtual<posAlvo)
  {
    vel = 1+posAlvo-posAtual;
    for (posAtual; posAtual<posAlvo; posAtual++)
    {
      sFiltro.write(posAtual+2);
      delay (passo + suav / vel);
      vel--;
    }
  }
  else if (posAtual>posAlvo)
  {
    vel = 1+posAtual-posAlvo;
    for (posAtual; posAtual>posAlvo; posAtual--)
    {
      sFiltro.write(posAtual-2);
      delay (passo + suav / vel);
      vel--;
    }
  }
  else
  {
    sFiltro.write(posAlvo);
  }
}

