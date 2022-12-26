package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {

	private WebDriver driver;

	private By nomeProduto = By.cssSelector(".product-line-info a");
	private By precoProduto = By.cssSelector("div.current-price span");
	private By tamanhoProduto = By.xpath("(//span[@class='value'])[1]");
	private By corProduto = By.xpath("(//span[@class='value'])[2]");
	private By input_quantidadeProduto = By.cssSelector(".js-cart-line-product-quantity");
	private By subtotalProduto = By.cssSelector("span.product-price strong");
	private By numeroItensTotal = By.cssSelector("span.js-subtotal");
	private By subtotalTotal = By.cssSelector("#cart-subtotal-products span.value");
	private By shippingTotal = By.cssSelector("#cart-subtotal-shipping span.value");
	private By totalTaxExclTotal = By.xpath("(//span[@class='value'])[5]");
	private By totalTaxInlTotal = By.xpath("(//span[@class='value'])[6]");
	private By taxasTotal = By.xpath("//span[@class='value sub']");

	private By botaoProceedToCheckout = By.cssSelector("a.btn-primary");

	public CarrinhoPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obter_nomeProduto() {
		return driver.findElement(nomeProduto).getText();
	}

	public String obter_precoProduto() {
		return driver.findElement(precoProduto).getText();
	}

	public String obter_tamanhoProduto() {
		return driver.findElement(tamanhoProduto).getText();
	}

	public String obter_corProduto() {
		return driver.findElement(corProduto).getText();
	}

	public String obter_input_quantidadeProduto() {
		return driver.findElement(input_quantidadeProduto).getAttribute("value");
	}

	public String obter_subtotalProduto() {
		return driver.findElement(subtotalProduto).getText();
	}

	public String obter_numeroItensTotal() {
		return driver.findElement(numeroItensTotal).getText();
	}

	public String obter_subtotalTotal() {
		return driver.findElement(subtotalTotal).getText();
	}

	public String obter_shippingTotal() {
		return driver.findElement(shippingTotal).getText();
	}

	public String obter_totalTaxExclTotal() {
		return driver.findElement(totalTaxExclTotal).getText();
	}

	public String obter_totalTaxIncTotal() {
		return driver.findElement(totalTaxInlTotal).getText();
	}

	public String obter_taxasTotal() {
		return driver.findElement(taxasTotal).getText();
	}

	public CheckoutPage clicarBotaoProceedToCheckout() {
		driver.findElement(botaoProceedToCheckout).click();
		return new CheckoutPage(driver);
	}

}
