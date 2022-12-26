package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class ModalProdutoPage {

	private WebDriver driver;

	private By mensagemProdutoAdicionado = By.id("myModalLabel");
	private By descricaoDoProduto = By.className("product-name");
	private By precoDoProduto = By.cssSelector("div.modal-body p.product-price");
	private By listaDeValoresInformados = By.cssSelector("div.divide-right div.col-md-6:nth-child(2) span strong");
	private By subTotal = By.cssSelector("div.cart-content  p:nth-child(2)  span:nth-child(2)");
	private By botaoProceedToCheckout = By.cssSelector("div.cart-content-btn a.btn-primary");

	public ModalProdutoPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obterMensagemProdutoAdicionado() {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5))
				.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionado));
		return driver.findElement(mensagemProdutoAdicionado).getText();
	}

	public String obterDescricaoProduto() {
		return driver.findElement(descricaoDoProduto).getText();
	}

	public String obterPrecoProduto() {
		return driver.findElement(precoDoProduto).getText();
	}

	public String obterTamanhoProduto() {
		return driver.findElements(listaDeValoresInformados).get(0).getText();
	}

	public String obterCorProduto() {
		return driver.findElements(listaDeValoresInformados).get(1).getText();
	}

	public String obterQuantidadeProduto() {
		return driver.findElements(listaDeValoresInformados).get(2).getText();
	}

	public String obterSubTotal() {
		return driver.findElement(subTotal).getText();
	}

	public CarrinhoPage clicarBotaoProceedToChekout() {
		driver.findElement(botaoProceedToCheckout).click();
		;
		return new CarrinhoPage(driver);
	}

}
