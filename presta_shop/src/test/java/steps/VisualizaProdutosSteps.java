package steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.HomePage;

public class VisualizaProdutosSteps {

	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);
	
	@BeforeAll
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver" ,"C:\\webdrivers\\chrome\\109.0.5414.25\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		new WebDriverWait(driver, Duration.ofSeconds(3));
	}
	
	@Dado("qeu estou na pagina inicial")
	public void qeu_estou_na_pagina_inicial() {
	   homePage.carregarPaginaInicial();
	   assertThat(homePage.obterTituloPagina(), is("Loja de Teste"));
	}

	@Quando("não estou logado")
	public void não_estou_logado() {
		 assertThat(homePage.estaLogado(), is(false));
	}

	@Entao("visualizo {int} produtos disponiveis")
	public void visualizo_produtos_disponiveis(Integer int1) {
		assertThat(homePage.contarProdutos(), is(int1));
	}

	@Entao("carrinho esta zerado")
	public void carrinho_esta_zerado() {
		assertThat(homePage.obterQuantidadeProdutosNoCarrinho(), is(8));
	}
	
	@After
	public static void finalizar() {
		driver.quit();
	}


}
