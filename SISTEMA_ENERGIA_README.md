# Sistema de Cálculo de Emissões de Energia - EcoTrack

## 📋 Visão Geral

O sistema de energia do EcoTrack foi criado seguindo a mesma arquitetura do sistema de transportes, permitindo aos usuários calcular as emissões de CO₂ de diferentes aparelhos eletrônicos e eletrodomésticos. O sistema integra com a API Climatiq para obter dados precisos de emissões.

## 🏗️ Arquitetura

### Modelos de Dados

#### 1. `TipoEnergia.kt`
- **Propósito**: Define os tipos de aparelhos/eletrônicos disponíveis
- **Características**:
  - 12 tipos de energia (eletrodomésticos, iluminação, energia renovável)
  - Potência média de cada aparelho em Watts
  - Fator de emissão CO₂ por kWh baseado no mix energético brasileiro
  - IDs específicos para a API Climatiq

#### 2. `DadosEnergia.kt`
- **Propósito**: Estruturas de dados para API e resultados
- **Componentes**:
  - `PeriodoTempo`: Enum para hora, dia, mês, ano
  - `RequisicaoEmissaoEnergia`: Request para API Climatiq
  - `ResultadoEmissaoEnergia`: Resultado do cálculo
  - `ComparacaoEmissaoEnergia`: Comparação com alternativas
  - `EstadoEnergia`: Singleton para estado global

### Serviços

#### 1. `ServicoApiEnergia.kt`
- **Propósito**: Interface Retrofit para API Climatiq
- **Endpoint**: `/estimate` para cálculo de emissões de energia

#### 2. `RepositorioEnergia.kt`
- **Propósito**: Lógica de negócio e integração com API
- **Funcionalidades**:
  - Cálculo de consumo em kWh baseado na potência e tempo
  - Chamadas para API Climatiq com fallback local
  - Criação de comparações com alternativas sustentáveis
  - Logs detalhados para debugging

### Telas

#### 1. `TelaCalculadoraEnergia.kt`
- **Propósito**: Interface para entrada de dados do usuário
- **Funcionalidades**:
  - Seleção de período (hora, dia, mês, ano)
  - Input de horas de uso
  - Seleção de tipo de aparelho
  - Cálculo automático de consumo estimado
  - Feedback visual com informações de debug

#### 2. `TelaResultadosEnergia.kt`
- **Propósito**: Exibição dos resultados e comparações
- **Funcionalidades**:
  - Resumo das emissões calculadas
  - Detalhes do consumo e potência
  - Comparação com alternativas sustentáveis
  - Dicas de economia de energia
  - Botão para novo cálculo

## 🔧 Funcionalidades Principais

### 1. Cálculo de Consumo
```
Consumo (kWh) = (Potência (W) × Horas × Multiplicador do Período) / 1000
```

### 2. Cálculo de Emissões
```
Emissões CO₂ = Consumo (kWh) × Fator de Emissão (kg CO₂/kWh)
```

### 3. Períodos Suportados
- **Hora**: Multiplicador 1.0
- **Dia**: Multiplicador 24.0
- **Mês**: Multiplicador 720.0 (24 × 30)
- **Ano**: Multiplicador 8760.0 (24 × 365)

### 4. Tipos de Energia Disponíveis

#### Eletrodomésticos
- Ar Condicionado (2000W)
- Geladeira (150W)
- Televisão (100W)
- Computador (200W)
- Lavadora (1500W)
- Microondas (1000W)
- Ferro de Passar (1200W)
- Chuveiro Elétrico (5500W)

#### Iluminação
- Luzes LED (10W)
- Luzes Incandescentes (60W)

#### Energia Renovável
- Energia Solar (0W - zero emissões)
- Energia Eólica (0W - zero emissões)

## 🌐 Integração com API Climatiq

### Configuração
- **Chave API**: `EWN9THXE5X3TNBG9ZD5KJ0VM0R`
- **URL Base**: `https://beta3.api.climatiq.io/`
- **Endpoint**: `/estimate`

### Request Example
```json
{
  "emission_factor": {
    "activity_id": "electricity-energy_source_grid-electricity_type_na-country_BR"
  },
  "parameters": {
    "energy": 4.8,
    "energy_unit": "kWh"
  }
}
```

### Response Example
```json
{
  "co2e": 0.566,
  "co2e_unit": "kg",
  "emission_factor": {
    "activity_id": "electricity-energy_source_grid-electricity_type_na-country_BR",
    "activity_name": "Electricity",
    "category": "electricity",
    "source": "Climatiq",
    "year": "2024",
    "region": "BR",
    "unit": "kWh",
    "unit_type": "energy"
  }
}
```

## 🚀 Navegação

### Fluxo de Navegação
1. **Home** → **Energia** → **Calculadora de Energia** → **Resultados**
2. Botão de calculadora (🧮) na tela de energia
3. Navegação entre telas com estado preservado

### Rotas Adicionadas
- `calculadora_energia`: Tela de entrada de dados
- `resultados_energia`: Tela de resultados e comparações

## 📊 Logs e Debugging

### Logs Implementados
```kotlin
Log.d("Energia", "⚡ Calculando emissões para: ${tipoEnergia.nome}")
Log.d("Energia", "📡 Chamando API: ${urlBase}estimate")
Log.d("Energia", "✅ Resposta da API: ${respostaEmissao.co2e}")
Log.e("Energia", "❌ Erro na API: ${response.code()}")
```

### Como Verificar Logs
```bash
# Android Studio Logcat
adb logcat | grep "Energia"

# Filtro específico
adb logcat | grep -E "(Energia|Climatiq)"
```

## 🎯 Benefícios e Alternativas

### Sistema de Comparação
O sistema compara automaticamente o aparelho selecionado com alternativas mais sustentáveis:

#### Benefícios por Tipo
- **Energia Solar**: Zero emissões, energia renovável, economia na conta
- **LED**: Baixo consumo, longa durabilidade, alta eficiência
- **Geladeira A++**: Eficiência energética, tecnologia inverter

#### Desvantagens Consideradas
- **Energia Solar**: Custo inicial alto, depende do sol
- **LED**: Custo inicial maior, sensibilidade à temperatura
- **Ar Condicionado**: Alto consumo, geração de calor

## 💡 Dicas de Sustentabilidade

O sistema inclui 8 dicas práticas para redução do consumo:
1. Desligue aparelhos quando não estiver usando
2. Use lâmpadas LED em vez de incandescentes
3. Configure o ar condicionado para 24°C
4. Use o chuveiro elétrico no modo verão
5. Desconecte carregadores quando não estiver carregando
6. Use a máquina de lavar com carga completa
7. Mantenha a geladeira longe de fontes de calor
8. Use o microondas em vez do forno quando possível

## 🔄 Fallback e Robustez

### Sistema de Fallback
- Se a API falhar, usa cálculo local baseado no fator de emissão
- Logs detalhados para identificar problemas
- Estado global preservado entre telas

### Tratamento de Erros
- Validação de entrada de dados
- Mensagens de erro amigáveis
- Indicadores de carregamento
- Fallback para dados mockados se necessário

## 📱 Interface do Usuário

### Design Responsivo
- Cards com elevação e cantos arredondados
- Cores consistentes com o tema EcoTrack
- Ícones intuitivos para cada tipo de aparelho
- Feedback visual para seleções

### Acessibilidade
- Textos descritivos para screen readers
- Contraste adequado de cores
- Botões com tamanho adequado para toque
- Navegação clara e intuitiva

## 🧪 Testes Sugeridos

### Cenários de Teste
1. **Ar Condicionado**: 2 horas por dia
2. **Geladeira**: 24 horas por dia
3. **TV**: 3 horas por dia
4. **Chuveiro Elétrico**: 0.5 horas por dia

### Verificação de Logs
- Confirmar chamadas para API Climatiq
- Verificar cálculos de consumo
- Validar comparações com alternativas
- Testar fallback em caso de erro de API

## 🎉 Conclusão

O sistema de energia do EcoTrack oferece uma solução completa para cálculo de emissões de CO₂ de aparelhos eletrônicos, com:

- ✅ Integração robusta com API Climatiq
- ✅ Interface intuitiva e responsiva
- ✅ Cálculos precisos baseados em dados reais
- ✅ Comparações com alternativas sustentáveis
- ✅ Sistema de fallback confiável
- ✅ Logs detalhados para debugging
- ✅ Dicas práticas de sustentabilidade

O sistema está pronto para uso e pode ser facilmente expandido com novos tipos de aparelhos ou funcionalidades adicionais.
