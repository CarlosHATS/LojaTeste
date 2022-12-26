# language: pt
Funcionalidade: Visualizar produtos
  Com o usuário não logado
  Eu quero visuzlizar produtos dispiníveis 
  Para poder escolher qual eu vou comprar

  Cenario: Deve mostar uma lista de oito produtos na pagina inicial
    Dado qeu estou na pagina inicial
    Quando não estou logado
    Entao visualizo 8 produtos disponiveis
    E carrinho esta zerado
