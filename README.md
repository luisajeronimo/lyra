# Lyra - Oráculo Digital 🔮<br>

O **Lyra** é uma API REST desenvolvida para unir a sabedoria do Tarot à tecnologia da Inteligência Artificial. <br>
O sistema oferece uma experiência de consulta personalizada, utilizando o modelo **Gemini 2.5 Flash** para interpretar arcanos maiores com base no perfil astrológico do usuário e nos trânsitos planetários do momento.<br>

# Funcionalidades<br>

- **Cálculo Automático de Signo:** Identificação do signo solar a partir da data de nascimento, implementada via Enum e gatilhos JPA (`@PrePersist`).<br>
- **Sorteio de Arcanos:** Seleção aleatória de cartas do deck de Arcanos Maiores armazenado em banco de dados.<br>
- **Interpretação por IA:** Motor de inteligência artificial que cruza os dados do consulente (nome e signo) com o simbolismo da carta sorteada e o céu astrológico do dia.<br>
- **Contextualização Astrológica:** O prompt é estruturado para que a IA considere posicionamentos astrológicos reais, tornando cada leitura única para a data da consulta.<br>
- **Documentação Interativa:** API documentada e testável via Swagger UI.<br>

# Tecnologias e Ferramentas<br>

- **Linguagem:** Java 21<br>
- **Framework:** Spring Boot 3.4.1<br>
- **Persistência:** Spring Data JPA / Hibernate<br>
- **Banco de Dados:** MySQL 8.0<br>
- **Inteligência Artificial:** Google GenAI SDK (Gemini 2.5 Flash)<br>
- **Documentação:** Swagger / OpenAPI 3<br>
- **Gerenciador de Dependências:** Maven<br>
- **Utilitários:** Lombok e Jakarta Validation<br>

# Arquitetura do Sistema<br>

O projeto segue os princípios da **Arquitetura em Camadas** e **SOLID**, garantindo baixo acoplamento e facilidade de manutenção.<br>

1. **Controller:** Orquestração das requisições HTTP e validação de dados com `@Valid`.<br>
2. **Service:** Camada de lógica de negócio e integração com a API do Google GenAI.<br>
3. **Repository:** Interfaces de comunicação com o MySQL.<br>
4. **Model:** Entidades de domínio e regras de negócio encapsuladas.<br>
