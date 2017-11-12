#include <PID_v1.h>

//definições do sistema de comunicação

//definições do sistema de controle
  //regulagem do termistor - Coef. de Steinhart-Hart
    #define coefA 0.001339011813
    #define coefB 0.0002141387336
    #define coefC 0.00000003269034923

  //temporização do filtro
    #define periodo 15        //em minutos
    #define tempoVazaoMax 1   //em períodos
    #define cicloTotal 4      //em períodos

  //regulagem do sistema PID
    #define aquecKp 50
    #define aquecKi 5
    #define aquecKd 50
    
    #define resfrKp 50
    #define resfrKi 5
    #define resfrKd 50
    
    #define tempoAmostra 500
    
  //regulagem da temperatura
    #define tolerancia 0.00 //°C
    #define fatorEvap 2.5   //°C
    #define fatorResfr 0.5  //°C

  //regulagem das médias de leituras
    #define qtdCiclos 60
    #define arred 0.001
    #define qtdDesprezada 5 // qtd de maiores e menores (valor x2)
    
  //portas de escrita dos bits de controle do filtro e da temperatura
    #define bT0 30
    #define bT1 32
    #define bT2 34
    #define bT3 36
    #define bT4 38

    #define bF0 40
    #define bF1 42

  //portas de leitura dos termístores
    #define termistor01 A14
    #define termistor02 A15
    #define termistor03 A12
    #define termistor04 A13

//variáveis de intercâmbio:
double tempAtual;
double tempSet;
int filtroAtual;
int filtroSet;

//variáveis do sistema de controle
int ctrlTemp[5], ctrlFiltro[2], iMedia = 0, iLoop = 0, i, tempoAtual, filtroOp, ctrlPID;
double valorResistencia[4], ctrlAquecPID, ctrlResfrPID, tempAguaAtual, tempArAtual, tempMediaAgua[qtdCiclos], tempMediaAr[qtdCiclos], tempCalcAgua[qtdCiclos], tempCalcAr[qtdCiclos];
PID aquec(&tempAguaAtual, &ctrlAquecPID, &tempSet, aquecKp, aquecKi, aquecKd, P_ON_M, DIRECT);
PID resfr(&tempAguaAtual, &ctrlResfrPID, &tempSet, resfrKp, resfrKi, resfrKd, P_ON_M, REVERSE);

void setup()
{
  //Inicialização do sistema de comunicação
  
//Inicialização do sistema de controle
  Serial.begin(230400);
  //Setup dos algoritmos PID
  aquec.SetMode(AUTOMATIC);
  aquec.SetOutputLimits(0, 15);
  aquec.SetSampleTime(tempoAmostra);
  
  resfr.SetMode(AUTOMATIC);
  resfr.SetOutputLimits(-10, 15);
  resfr.SetSampleTime(tempoAmostra);

  //Setup dos barramentos
  pinMode(bT0, OUTPUT);
  pinMode(bT1, OUTPUT);
  pinMode(bT2, OUTPUT);
  pinMode(bT3, OUTPUT);
  pinMode(bT4, OUTPUT);
  pinMode(bF0, OUTPUT);
  pinMode(bF1, OUTPUT);

  filtroAtual = 0;
  tempAguaAtual = 0;
  tempArAtual = 0;
  
  for (i=0; i<qtdCiclos; i++)
  {
    tempMediaAgua[i] = (lerTermistor(termistor01)+lerTermistor(termistor02))/2;
  }
  for (i=0; i<qtdCiclos; i++)
  {
    tempMediaAr[i] = (lerTermistor(termistor03)+lerTermistor(termistor04))/2;
  }
}

void loop() {

  tempSet = 27;
  filtroSet = 2;

//Início do sistema de controle

  //Cálculo das médias das temperaturas ----------------------------------------------
    if (iMedia > qtdCiclos)
    {
      iMedia = 0;
    }
    
    tempMediaAgua[iMedia] = (lerTermistor(termistor01) + lerTermistor(termistor02))/2;
    tempMediaAr[iMedia] = (lerTermistor(termistor03) + lerTermistor(termistor04))/2;
    
    iMedia++;
    
    tempAguaAtual = 0;
    tempArAtual = 0;
    
    for (i=0; i<qtdCiclos; i++)
    {
      tempCalcAgua[i] = tempMediaAgua[i];
      tempCalcAr[i] = tempMediaAr[i];
    }
    
    BubbleSort(tempCalcAgua, qtdCiclos);
    BubbleSort(tempCalcAr, qtdCiclos);
    
    for (i=qtdDesprezada; i<qtdCiclos-qtdDesprezada; i++)
    {
      tempAguaAtual = tempAguaAtual + tempCalcAgua[i];
      tempArAtual = tempArAtual + tempCalcAr[i];
    }
    
    tempAguaAtual = tempAguaAtual / (qtdCiclos - qtdDesprezada * 2);
    tempArAtual = tempArAtual / (qtdCiclos - qtdDesprezada * 2);
    
    tempAguaAtual = (1/arred) * tempAguaAtual;
    tempAguaAtual = round(tempAguaAtual);
    tempAguaAtual = tempAguaAtual / (1/arred);
    
    tempAtual = tempAguaAtual;
    
    tempArAtual = (1/arred) * tempArAtual;
    tempArAtual = round(tempArAtual);
    tempArAtual = tempArAtual / (1/arred);

  //Cálculo do algoritmo PID --------------------------------------------------------
    aquec.Compute();
    resfr.Compute();
    
    if (tempSet <= tempArAtual - fatorEvap)
    {
      ctrlPID = -ctrlResfrPID;
    }
    else if (tempAguaAtual > tempSet + fatorResfr)
    {
      ctrlPID = -ctrlResfrPID;
    }
    else if (tempSet > tempArAtual - fatorEvap)
    {
      ctrlPID = ctrlAquecPID;
    }
    
  //Cálculo do ciclo do filtro ------------------------------------------------------
    tempoAtual = millis()/1000/60/periodo%(cicloTotal);
    
    if (filtroSet == 0)
      filtroOp = 0;
    else if (filtroSet == 1)
      filtroOp = 1;
    else if (filtroSet ==2)
    {
      if (tempoAtual < tempoVazaoMax)
        filtroOp = 1;
      else if (tempoAtual >= tempoVazaoMax)
        filtroOp = 2;
    }
    
  //Codicifação dos valores para os módulos de controle -----------------------------
    encodeCtrlTemp (ctrlPID);
    encodeCtrlFiltro (filtroOp);
    
    enviaCtrlTemp ();
    enviaCtrlFiltro ();

    filtroAtual = decodeCtrlFiltro();

  //Envio dos valores por Serial (remover quando o sistema estiver completo) -------
    Serial.println("---------------");
    Serial.print("tempSet: ");
    Serial.println(tempSet);
    Serial.print("filtroSet: ");
    Serial.println(filtroSet);
    Serial.println(" - - - - - - - ");
    Serial.print("tempAguaAtual: ");
    Serial.println(tempAguaAtual);
    Serial.print("tempArAtual: ");
    Serial.println(tempArAtual);
    Serial.println(" - - - - - - - ");
    Serial.print("PID: ");
    Serial.print(ctrlPID);
    Serial.print(" (aquec: ");
    Serial.print(ctrlAquecPID);
    Serial.print(", resfr: ");
    Serial.print(ctrlResfrPID);
    Serial.println(")");
    Serial.println(" - - - - - - - ");
    Serial.print("ctrlTemp: ");
    Serial.print(ctrlTemp[4]);
    Serial.print(":");
    Serial.print(ctrlTemp[0]);
    Serial.print(ctrlTemp[1]);
    Serial.print(ctrlTemp[2]);
    Serial.println(ctrlTemp[3]);
    Serial.print("ctrlFiltro: ");
    Serial.print(ctrlFiltro[0]);
    Serial.println(ctrlFiltro[1]);
    Serial.print("filtroAtual: ");
    Serial.println(filtroAtual);
    Serial.println(" - - - - - - - ");
    Serial.print("tempoAtual: ");
    Serial.println(tempoAtual);
    delay(300);
  
  //Fim do sistema de controle;
}

//Funções usadas pelo sistema de controle -------------------------------------------

void BubbleSort(double vetor[], int tamanho) //Função de ordenação de vetores
{
  double aux;
  int i, j;
  for(j=tamanho-1; j>=1; j--)
  {
    for(i=0; i<j; i++)
    {
      if(vetor[i]>vetor[i+1])
      {
        aux=vetor[i];
        vetor[i]=vetor[i+1];
        vetor[i+1]=aux;
      }
    }
  }
}

double lerTermistor(int entrada)                                            //Lê o Termistor na entrada analógica "entrada"
{
 double Temp;
 double inVal = analogRead(entrada);                                        //lê o valor do Termistor
 double rVal = ((5/((inVal/1024)*5))-1)*10000;                              //converte o valor lido para um valor de resistência
 double logVal = log(rVal);                                                 //faz o logarítmo do valor de resistência
 Temp = 1/(coefA + coefB * logVal  + coefC * logVal * logVal * logVal);     //calcula a temperatura através da fórmula de Steinhart-Hart
 Temp = Temp - 273.15;                                                      //converte para °C e soma a calibragem
 return Temp;                                                               //retorna o valor em °C
}

void encodeCtrlTemp (int PIDTemp)                                 //Converte o valor de controle da saída do PID em valor Binário com indicação de Resfriamento ou Aquecimento
{
  if (PIDTemp > 0)
  {
    int valor = PIDTemp;
    ctrlTemp[4] = 1;
    ctrlTemp[3] = valor % 2;
    valor = valor / 2;
    ctrlTemp[2] = valor % 2;
    valor = valor / 2;
    ctrlTemp[1] = valor % 2;
    valor = valor / 2;
    ctrlTemp[0] = valor % 2;
  }
  else if (PIDTemp < 0)
  {
    int valor = -PIDTemp;
    ctrlTemp[4] = 0;
    ctrlTemp[3] = valor % 2;
    valor = valor / 2;
    ctrlTemp[2] = valor % 2;
    valor = valor / 2;
    ctrlTemp[1] = valor % 2;
    valor = valor / 2;
    ctrlTemp[0] = valor % 2;
  }
  else if (PIDTemp == 0)
  {
    ctrlTemp[4] = 0;
    ctrlTemp[3] = 0;
    ctrlTemp[2] = 0;
    ctrlTemp[1] = 0;
    ctrlTemp[0] = 0;
  }
}

void encodeCtrlFiltro (int Filtro)                                 //Converte o valor de controle da saída do PID em valor Binário com indicação de Resfriamento ou Aquecimento
{
  if (Filtro > 0)
  {
    int valor = Filtro;
    ctrlFiltro[1] = valor % 2;
    valor = valor / 2;
    ctrlFiltro[0] = valor % 2;
  }
  else if (Filtro == 0)
  {
    ctrlFiltro[1] = 0;
    ctrlFiltro[0] = 0;
  }
}

void enviaCtrlTemp ()
{
  if (ctrlTemp[0] == 0)
    digitalWrite(bT0, LOW);
  else if (ctrlTemp[0] == 1)
    digitalWrite(bT0, HIGH);

  if (ctrlTemp[1] == 0)
    digitalWrite(bT1, LOW);
  else if (ctrlTemp[1] == 1)
    digitalWrite(bT1, HIGH);
  
  if (ctrlTemp[2] == 0)
    digitalWrite(bT2, LOW);
  else if (ctrlTemp[2] == 1)
    digitalWrite(bT2, HIGH);

  if (ctrlTemp[3] == 0)
    digitalWrite(bT3, LOW);
  else if (ctrlTemp[3] == 1)
    digitalWrite(bT3, HIGH);
  
  if (ctrlTemp[4] == 0)
    digitalWrite(bT4, LOW);
  else if (ctrlTemp[4] == 1)
    digitalWrite(bT4, HIGH);
}

void enviaCtrlFiltro ()
{
  if (ctrlFiltro[0] == 0)
    digitalWrite(bF0, LOW);
  else if (ctrlFiltro[0] == 1)
    digitalWrite(bF0, HIGH);

  if (ctrlFiltro[1] == 0)
    digitalWrite(bF1, LOW);
  else if (ctrlFiltro[1] == 1)
    digitalWrite(bF1, HIGH);
}

int decodeCtrlFiltro()                                 //Converte o valor de controle binário com indicação de Resfriamento ou Aquecimento em sinal.
{
  int estadoAtual = 0;
  
  if (ctrlFiltro[1] == 1)
    estadoAtual = estadoAtual + 1;
  
  if (ctrlFiltro[0] == 1)
    estadoAtual = estadoAtual + 2;
  return estadoAtual;
}

