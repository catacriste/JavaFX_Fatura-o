-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 09-Jul-2015 às 03:53
-- Versão do servidor: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `faturacao`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `codCivil` int(10) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(255) DEFAULT NULL,
  `NIF` int(10) NOT NULL,
  `Morada` varchar(255) DEFAULT NULL,
  `NIB` int(10) NOT NULL,
  `NISS` int(10) NOT NULL,
  PRIMARY KEY (`codCivil`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`codCivil`, `Nome`, `NIF`, `Morada`, `NIB`, `NISS`) VALUES
(1, 'David Sousa', 3242334, 'Rua da Escola', 142343, 1341243),
(2, 'Ruben', 23312, 'Rua da Pascoa', 213213, 124412),
(3, 'Ruben', 23312, 'Rua da Pascoa', 213213, 124412);

-- --------------------------------------------------------

--
-- Estrutura da tabela `fatura`
--

CREATE TABLE IF NOT EXISTS `fatura` (
  `codFatura` int(10) NOT NULL AUTO_INCREMENT,
  `ClientecodCivil` int(10) NOT NULL,
  `DataDaFatura` varchar(255) DEFAULT NULL,
  `Garantia` varchar(255) DEFAULT NULL,
  `Total` float NOT NULL,
  PRIMARY KEY (`codFatura`),
  KEY `Tem` (`ClientecodCivil`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Extraindo dados da tabela `fatura`
--

INSERT INTO `fatura` (`codFatura`, `ClientecodCivil`, `DataDaFatura`, `Garantia`, `Total`) VALUES
(1, 2, '2 de Outubro', '4 Anos', 300),
(2, 1, '31-01-2015', 'Até 2 de janeiro', 321),
(3, 2, '25-01-2015', '3 Anos', 300),
(4, 3, '31-01-2015', '5 Anos', 190),
(6, 2, '1-02-2015', '1 Ano', 100),
(7, 2, '09-12-2014', '1 Ano', 700);

-- --------------------------------------------------------

--
-- Estrutura da tabela `fatura_produto`
--

CREATE TABLE IF NOT EXISTS `fatura_produto` (
  `FaturacodFatura` int(10) NOT NULL,
  `ProdutocodProduto` int(10) NOT NULL,
  PRIMARY KEY (`FaturacodFatura`,`ProdutocodProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `produto`
--

CREATE TABLE IF NOT EXISTS `produto` (
  `codProduto` int(10) NOT NULL AUTO_INCREMENT,
  `NomeProduto` varchar(255) DEFAULT NULL,
  `Marca` varchar(255) DEFAULT NULL,
  `DataValidade` varchar(255) DEFAULT NULL,
  `Preco` varchar(255) DEFAULT NULL,
  `Stock` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codProduto`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `produto`
--

INSERT INTO `produto` (`codProduto`, `NomeProduto`, `Marca`, `DataValidade`, `Preco`, `Stock`) VALUES
(1, 'Galaxy S6', 'Samsung', 'Não Tem', '599€', '20');

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `fatura`
--
ALTER TABLE `fatura`
  ADD CONSTRAINT `Tem` FOREIGN KEY (`ClientecodCivil`) REFERENCES `cliente` (`codCivil`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
