package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

	private WebDriver driver;

	List<WebElement> listaProdutos = new ArrayList();

	private By produtos = By.cssSelector("div.product-description");
	private By textoProdutosNoCarrinho = By.className("cart-products-count");
	private By descricoesDosProdutos = By.cssSelector("div.product-description a");
	private By clicarNaLixeiraDoCarrinho = By.xpath("//i[contains(.,'delete')]");
	private By precosDosProdutos = By.className("price");
	private By botaoSigIn = By.cssSelector("#_desktop_user_info span.hidden-sm-down");
	private By usuarioLogado = By.cssSelector("#_desktop_user_info span.hidden-sm-down");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	private void carregarListaDeProdutos() {
		listaProdutos = driver.findElements(produtos);
	}

	public int contarProdutos() {
		carregarListaDeProdutos();
		return listaProdutos.size();
	}

	public int obterQuantidadeProdutosNoCarrinho() {
		String quantidadeProdutosNoCarrinho = driver.findElement(textoProdutosNoCarrinho).getText();
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace("(", "");
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace(")", "");
		int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutosNoCarrinho);
		return qtdProdutosNoCarrinho;
	}

	public void zerarProdutosNoCarrinho() {
		String quantidadeProdutosNoCarrinho = driver.findElement(textoProdutosNoCarrinho).getText();
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace("(", "");
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace(")", "");
		int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutosNoCarrinho);
		if (qtdProdutosNoCarrinho == 0) {
		} else {
			driver.findElement(textoProdutosNoCarrinho).click();
			driver.findElement(clicarNaLixeiraDoCarrinho).click();
		}

	}

	public String obterNomeProduto(int indice) {
		return driver.findElements(descricoesDosProdutos).get(indice).getText();
	}

	public String obterPrecoProduto(int indice) {
		return driver.findElements(precosDosProdutos).get(indice).getText();
	}

	public ProdutoPage clicarProduto(int indice) {
		driver.findElements(descricoesDosProdutos).get(indice).click();
		return new ProdutoPage(driver);
	}

	public LoginPage clicarBotaoSigIn() {
		driver.findElement(botaoSigIn).click();
		return new LoginPage(driver);
	}

	public boolean estaLogado(String texto) {
		return texto.contentEquals(driver.findElement(usuarioLogado).getText());
	}

	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
	}

	public String obterTituloPagina() {
		return driver.getTitle();
	}

	public boolean estaLogado() {
		return !"Sign in".contentEquals(driver.findElement(usuarioLogado).getText());
	}

}
