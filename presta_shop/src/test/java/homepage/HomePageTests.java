package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTests extends BaseTests {

	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8));
	}

	@Test
	public void testValidarCarrinhoZerado_zeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		assertThat(produtosNoCarrinho, is(0));
	}

	ProdutoPage produtoPage;
	String nomeProduto_ProdutoPage;

	@Test
	public void testValidarDetalhesDoProduto_descricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		produtoPage = homePage.clicarProduto(indice);
		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));
	}

	LoginPage loginPage;

	@Test
	public void testLoginComSucesso_usuarioLogado() {
		// clicar no botao Sign na home page
		loginPage = homePage.clicarBotaoSigIn();

		// Preencher usuario e ssenha
		loginPage.preencherEmail("carlos1@gmail.com");
		loginPage.preencherPassword("12345678");

		// clicar botao Sig In
		loginPage.clicarBtSign();

		// valida se usuario está de fato logado
		assertThat(homePage.estaLogado("carlos silva"), is(true));

		carregarPaginaInicial();
	}

	ModalProdutoPage modalProdutoPage;

	@Test
	public void testIncluirProdutoNoCarrinho_produtoIncluidoComSucesso() {
		// pre-condicao usuario logado
		if (!homePage.estaLogado("carlos silva")) {
			testLoginComSucesso_usuarioLogado();
		}
		carregarPaginaInicial();
		homePage.zerarProdutosNoCarrinho();
		carregarPaginaInicial();
		// teste selecionando produto
		testValidarDetalhesDoProduto_descricaoEValorIguais();
		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;

		// selecionar tamanho
		List<String> listaOpceos = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpceos.get(0));
		System.out.println("tamanho da lista: " + listaOpceos.size());

		produtoPage.selecionarOpcaoDropDown(tamanhoProduto);
		listaOpceos = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpceos.get(0));
		System.out.println("tamanho da lista: " + listaOpceos.size());

		// selecionar cor
		produtoPage.selecionarCorPreta();

		// selecionar auantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		// adicionar ao carrinho
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// validacoes
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
				.contains("Product successfully added to your shopping cart"));
		assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));

		String precoProdutoString = modalProdutoPage.obterPrecoProduto();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);

		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
		assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));

		System.out.println(modalProdutoPage.obterSubTotal());
		String subTotalString = modalProdutoPage.obterSubTotal();
		subTotalString = subTotalString.replace("$", "");
		Double subTotal = Double.parseDouble(subTotalString);

		Double subTotalCalculado = quantidadeProduto * precoProduto;
		System.out.println(subTotalCalculado);
		assertThat(subTotal, is(subTotalCalculado));
	}

	// Valores esperados
	String esperado_nomeProduto = "Hummingbird printed t-shirt";
	Double esperado_precoProduto = 19.12;
	String esperado_tamanhoProduto = "M";
	String esperado_corProduto = "Black";
	int esperado_input_quantidadeProduto = 2;
	Double esperado_subtotalProduto = esperado_precoProduto * esperado_input_quantidadeProduto;

	int esperado_numeroItensTotal = esperado_input_quantidadeProduto;
	Double esperado_subtotalTotal = esperado_subtotalProduto;
	Double esperado_shippingTotal = 7.00;
	Double esperado_totalTaxExclTotal = esperado_subtotalTotal + esperado_shippingTotal;
	Double esperado_totalTaxIncTotal = esperado_totalTaxExclTotal;
	Double esperado_taxasTotal = 0.00;
	String esperado_nomeCliente = "carlos silva";

	CarrinhoPage carrinhoPage;

	@Test
	public void IrParaCarrinho_infirmacoesPersistidas() {
		// Pré-ondicao
		// Produto incluído na tela ModalProdutoPage
		testIncluirProdutoNoCarrinho_produtoIncluidoComSucesso();
		carrinhoPage = modalProdutoPage.clicarBotaoProceedToChekout();
		// Asserts
		assertThat(carrinhoPage.obter_nomeProduto(), is(esperado_nomeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()), is(esperado_precoProduto));
		assertThat(carrinhoPage.obter_tamanhoProduto(), is(esperado_tamanhoProduto));
		assertThat(carrinhoPage.obter_corProduto(), is(esperado_corProduto));
		assertThat(Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()),
				is(esperado_input_quantidadeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()),
				is(esperado_subtotalProduto));

		assertThat(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()),
				is(esperado_numeroItensTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()), is(esperado_subtotalTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()), is(esperado_shippingTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()),
				is(esperado_totalTaxExclTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()),
				is(esperado_totalTaxIncTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxasTotal()), is(esperado_taxasTotal));
	}

	CheckoutPage checkoutPage;

	@Test
	public void testIrParaCheckout_freteMeioDePagamentoEnderecoListadoOK() {
		// Pré-condiçoes

		// Produto dispinível no carrinho de compras
		IrParaCarrinho_infirmacoesPersistidas();

		// Clciar botão
		checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();

		// Preencher informações

		// Validade informações
		assertThat(Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_totalTaxIncTotal()),
				is(esperado_totalTaxIncTotal));
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));

		checkoutPage.clicarBotaoContinueAdress();

		String encontrado_shippingValor = checkoutPage.obter_shippingValor();
		encontrado_shippingValor = Funcoes.removeTexto(encontrado_shippingValor, " tax excl.");
		Double encontrado_shippingValor_Double = Funcoes.removeCifraoDevolveDouble(encontrado_shippingValor);

		assertThat(encontrado_shippingValor_Double, is(esperado_shippingTotal));

		checkoutPage.clicarBotaoContinueShipping();

		// Sleceionar opçao "pay by check"
		checkoutPage.selecionarRadioPayByCheck();

		// validar valor do cheque (amount)
		String econtrado_amountPayByCheck = checkoutPage.obter_amountPayByCheck();
		econtrado_amountPayByCheck = Funcoes.removeTexto(econtrado_amountPayByCheck, " (tax incl.)");
		Double econtrado_amountPayByCheck_Double = Funcoes.removeCifraoDevolveDouble(econtrado_amountPayByCheck);
		assertThat(econtrado_amountPayByCheck_Double, is(esperado_totalTaxIncTotal));

		// Clicar na opção "I agree"
		checkoutPage.clicarCheckboxIAgree();
		assertTrue(checkoutPage.estaSelecionadoCheckBoxIAgree());
	}
	
}
