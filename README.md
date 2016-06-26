##VBank##

Este projeto visa a demostração do aprendizado em JavaEE utilizando EJB.


###Cenário###
Com a nova regulamentação bancária no Brasil, novos tipos de negócios tornaram-se viáveis. Um deles é possibilidade de abertura de bancos virtuais, que não necessariamente precisam ter uma agência disponível para o cliente entrar em contato pessoalmente.
Enxergando o enorme potencial que esta mudança proporcionou, um grupo de investidores americanos (atualmente detentores de uma rede bancária nos EUA), resolveu criar o VBank, o banco totalmente virtual que será disponibilizado para o mercado brasileiro.
O banco pretende oferecer a seus clientes serviço como disponibilização de cartões de crédito, contas correntes e contas poupança. O mesmo visa atingir um público jovem, universitário e altamente conectado a internet.


###Histórias de usuário###
1. Eu, como pretendente a cliente, preciso submeter a minha proposta de abertura de
conta ao VBank, totalmente online, sem a necessidade de me dirigir a uma agência
física.
2. Eu, como proponente a abertura de uma conta, preciso receber por e-mail, o número
da conta, bem como uma forma de acesso e cadastro da primeira senha de acesso.
3. Eu, como cliente do VBank, preciso realizar transferências bancárias para outroclientes do Vbank, bem como para clientes de bancos tradicionais.
4. Eu, como cliente do VBank, preciso realizar depósitos em cheque na minha conta
corrente
5. Eu, como cliente do Vbank, preciso consultar minha movimentação bancária, seja ela
realizada na conta corrente, conta poupança ou ainda as despesas realizadas no
cartão de crédito e débito
6. Eu, como cliente do VBank, preciso realizar o pagamento de contas simples, como
boletos de cobrança, contas de água e luz e impostos.
7. Eu como cliente do Vbank, gostaria de realizar o agendamento do pagamento de
contas, bem como, caso precise, cancelar o agendamento.
8. Eu como gerente de contas do VBank, preciso visualizar todas as propostas recebidas
por clientes de uma determinada região do país.
9. Eu como gerente de contas do Vbank, preciso aceitar ou rejeitar propostas. No caso da
aceitação da proposta, uma nova conta deve ser criada e enviado ao proponente um e- mail com o número da conta, bem como o meio para o cadastramento da primeira senha de acesso ao sistema. No caso de rejeitá-lo, deve-se enviar um e-mail ao proponente, expondo o motivo pelo qual foi negada a sua proposta.
10. Eu, como gerente de segurança do VBank, preciso configurar no sistema, horários nos quais as transações não podem mais ser executadas.
11. Eu, como gerente de operações, preciso garantir que todas as transações realizadas no Vbank, foram integradas ao sistema atual (disponibilizado pelo sistema nos EUA) e que também foram integradas ao Banco Central do Brasil. Para isto, preciso monitorar todas as transações e se necessário integra-las manualmente, disparando o processo de integração.


###Requisitos não funcionais###
1. O sistema deve suportar inicialmente 100 acessos concorrentes. Em 3 meses, o sistema deverá suportar 5000 acessos concorrentes.
2. O sistema deve fornecer níveis diferenciados de acesso a informações, ou seja, cada grupo de usuários somente pode ter acesso a informações pertinentes a sua área, ou seja, gerentes de contas, somente tem acesso a dados dos clientes, contas dos clientes, movimentações e propostas de cadastro. Gerente de operações somente podem acessar os serviços referentes a integração de sistemas e clientes, somente podem visualizar dados referentes a sua conta.
3. Todas as transações devem ser auditadas(log), sejam elas realizadas com sucesso ou não.
4. As integrações são realizadas a cada 5 minutos com o Banco Central do Brasil e somente após as 22 horas com os EUA.
5. As operações realizadas por clientes podem acontecer entre 6h até as 21h30 minutos. Estes horários durante os vários períodos do ano sofrem alterações. Nenhuma operação de transferência ou movimentação pode ser feito fora deste horário. As operações de consulta podem ser realizadas normalmente em qualquer horário.

###Outras regras importantes###
* Nao deve ser permitido que um CPF inválido seja informado na proposta de abertura
de conta
* Um CPF somente pode enviar uma proposta a cada 30 dias. Caso a primeira proposta
seja negada, o mesmo somente pode enviar nova proposta após 30 dias. Caso tenha
sido aceita a primeira proposta, não deve permitir enviar nehuma outra proposta
* Nao deve ser permitido que duas pessoas diferentes tenham o mesmo CPF 

###Informações importantes###
- servidor de aplicação disponibilizado possui 24 núcleos, sendo que os investidores
solicitam que seja usado todo o poder de processamento em paralelo deste servidor.
- A interface gráfica será terceirizada para uma equipe que está na China e logo não faz
parte do escopo deste projeto nesta etapa.

