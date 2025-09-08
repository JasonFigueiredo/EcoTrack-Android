# Calculadora de Emissões de CO₂ - EcoTrack

## Funcionalidades Implementadas

### 🚗 Calculadora de Emissões
- **Localização**: Acessível através da tela de Transporte → Botão "Calculadora de Emissões" (ícone de calculadora)
- **Funcionalidade**: Permite ao usuário selecionar um tipo de transporte e inserir a distância percorrida
- **Tipos de Transporte Disponíveis**:
  - Carro (Gasolina) - 0.192 kg CO₂/km
  - Carro (Diesel) - 0.171 kg CO₂/km  
  - Carro (Elétrico) - 0.053 kg CO₂/km
  - Moto - 0.113 kg CO₂/km
  - Ônibus - 0.089 kg CO₂/km
  - Trem - 0.041 kg CO₂/km
  - Bicicleta - 0.0 kg CO₂/km

### 📊 Tela de Resultados
- **Exibe**: Emissões calculadas do transporte selecionado
- **Comparações**: Mostra alternativas mais sustentáveis
- **Sugestões**: Lista benefícios e considerações de cada alternativa
- **Dicas**: Inclui dicas de sustentabilidade para reduzir emissões

### 🔗 Integração com API Climatiq
- **API Key**: EWN9THXE5X3TNBG9ZD5KJ0VM0R
- **Endpoint**: https://beta3.api.climatiq.io/estimate
- **Funcionalidade**: Calcula emissões reais baseadas em dados científicos
- **Fallback**: Sistema funciona com dados mockados se a API não estiver disponível

## Como Usar

1. **Acesse a Calculadora**:
   - Vá para a tela "Transporte" no app
   - Toque no ícone de calculadora (🧮) no canto superior direito

2. **Calcule suas Emissões**:
   - Digite a distância percorrida em quilômetros
   - Selecione o tipo de transporte usado
   - Toque em "Calcular Emissões"

3. **Analise os Resultados**:
   - Veja suas emissões de CO₂
   - Compare com alternativas mais sustentáveis
   - Leia as dicas de sustentabilidade

## Arquivos Criados/Modificados

### Novos Arquivos:
- `model/TransportType.kt` - Modelos de dados para tipos de transporte
- `model/EmissionData.kt` - Modelos para dados de emissões e respostas da API
- `service/ClimatiqApiService.kt` - Interface para a API do Climatiq
- `service/ClimatiqRepository.kt` - Repositório para gerenciar chamadas da API
- `ui/screens/EmissionCalculatorScreen.kt` - Tela de cálculo de emissões
- `ui/screens/EmissionResultsScreen.kt` - Tela de resultados e comparações

### Arquivos Modificados:
- `navigation/EcoTrackNavigation.kt` - Adicionada navegação para novas telas
- `ui/screens/TransportScreen.kt` - Adicionado botão para calculadora
- `ui/screens/AddTransportScreen.kt` - Atualizado para usar novos modelos
- `ui/screens/HomeScreen.kt` - Atualizado para usar novos modelos

## Tecnologias Utilizadas

- **Retrofit**: Para chamadas HTTP à API
- **Gson**: Para serialização/deserialização JSON
- **Coroutines**: Para operações assíncronas
- **Jetpack Compose**: Para interface do usuário
- **Navigation Compose**: Para navegação entre telas

## Próximos Passos

1. **Integração Real da API**: Substituir dados mockados por chamadas reais
2. **Persistência**: Salvar histórico de cálculos
3. **Gráficos**: Adicionar visualizações de tendências
4. **Notificações**: Alertas sobre metas de redução
5. **Compartilhamento**: Permitir compartilhar resultados

## Configuração da API

Para usar a API real do Climatiq, certifique-se de que:
- A chave API está válida
- O dispositivo tem conexão com a internet
- As permissões de rede estão configuradas no AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
