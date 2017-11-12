
#define b0 11
#define b1 10
#define b2 9
#define b3 8
#define b4 12
#define ctrlAquecPin 6
#define ctrlResfrPin 3

volatile int valor = 0, operacao = 0;
volatile int aquecPower = 0, resfrPower = 0;

void setup()
{
  TCCR2B = (TCCR2B & 0b11111000) | 0x07;
  Serial.begin(9600);
  pinMode(ctrlAquecPin, OUTPUT);
  pinMode(ctrlResfrPin, OUTPUT);
  
  attachInterrupt(0, ctrlAquec, RISING);
  
  pinMode(b0, INPUT);
  pinMode(b1, INPUT);
  pinMode(b2, INPUT);
  pinMode(b3, INPUT);
  pinMode(b4, INPUT);
}

void loop()
{
  
  decodeCtrlTemp ();
  
  if (operacao == 0)
  {
    aquecPower = 0;
    resfrPower = valor;
  }
  else if (operacao == 1)
  {
    aquecPower = valor;
    resfrPower = 0;
  }
  
  ctrlResfr ();

  Serial.println("----------------------");
  Serial.print("ctrlTemp: ");
  Serial.print(operacao);
  Serial.print(":");
  Serial.println(valor);
  Serial.print("aquecPower: ");
  Serial.println(aquecPower);
  Serial.print("resfrPower: ");
  Serial.println(resfrPower);
  Serial.print("bits: ");
  Serial.print(digitalRead(b4));
  Serial.print(":");
  Serial.print(digitalRead(b3));
  Serial.print(digitalRead(b2));
  Serial.print(digitalRead(b1));
  Serial.println(digitalRead(b0));
  delay (500);

}

void ctrlAquec()
{
  //Cálculo do ângulo de disparo: 60Hz -> 8,33ms (1/2 ciclo)
  //(8333us - 8,33us) / 100 = 83 (aproximadamente)
  //O powerTime é o tempo que o TRIAC permanecerá desligado quando é detectado
  //o ponto 0 da senóide e varia de 0 a 8300 microsegundos
  int powerTime = (82 * (100 - aquecPower));
  //Se o powerTime for menor ou igual a 820 microsegundos, dar o comando de
  //ligar a lâmpada (carga) - potência total fornecida
  if (powerTime <= 820)
  {
    //Liga o pulso do sinal ao TRIAC para que ele passe a conduzir, coloca o
    //pino digital "ctrlAquecPin" em nível alto
    digitalWrite(ctrlAquecPin, HIGH);
  }
  //Se o powerTime for menor ou igual a 8000 microsegundos, dar o comando de
  //desligar a lâmpada (carga) - potência zero fornecida
  else if (powerTime >= 8000)
  {
    //Desliga o pulso do sinal ao TRIAC para que ele não conduza, coloca o pino
    //digital "ctrlAquecPin" em nível baixo
    digitalWrite(ctrlAquecPin, LOW);
  }
  //Se o powerTime estiver entre 820 microsegundos a 8000 microsegundos
  else if ((powerTime > 820) && (powerTime < 8000))
  {
    //Mantém o circuito desligado por powerTime microssegundos (espera powerTime
    //microssegundos)
    delayMicroseconds(powerTime);
  
    //Envia sinal ao TRIAC para que ele passe a conduzir, coloca o pino digital
    //"ctrlAquecPin" em nível alto
    digitalWrite(ctrlAquecPin, HIGH);
  
    //Espera 8 microssegundos para que o TRIAC perceba o pulso
    delayMicroseconds(8);
    //Desliga o pulso do TRIAC, coloca o pino digital "ctrlAquecPin" em nível baixo
    digitalWrite(ctrlAquecPin, LOW);
  }
}

void ctrlResfr ()
{
  analogWrite (ctrlResfrPin, (255 * resfrPower / 100));
}

void decodeCtrlTemp ()                                 //Converte o valor de controle binário com indicação de Resfriamento ou Aquecimento em sinal.
{
  
  valor = 0;
  operacao = 0;
  
  if (digitalRead(b0) == HIGH)
  {
    valor=valor+1;
  }
  if (digitalRead(b1) == HIGH)
  {
    valor=valor+2;
  }
  if (digitalRead(b2) == HIGH)
  {
    valor=valor+4;
  }
  if (digitalRead(b3) == HIGH)
  {
    valor=valor+8;
  }

  valor = (valor * 100)/15;
  
  if (digitalRead(b4) == HIGH)
  {
    operacao = 1;
  }
  else if (digitalRead(b4) == LOW)
  {
    operacao = 0;
  }
  
}
