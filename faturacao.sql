-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 27-Jul-2015 às 05:22
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`codCivil`, `Nome`, `NIF`, `Morada`, `NIB`, `NISS`) VALUES
(1, 'David Sousa', 3242334, 'Rua do Eduardo', 142343, 1341243),
(2, 'Ruben', 23312, 'Rua da Pascoa', 213213, 124412),
(3, 'Mariana', 23312, 'Rua do Natal', 34334, 664456),
(7, 'Eduardo Barros', 2143412, 'Rua onde o Bacalhau se pesca', 3241244, 2144334),
(8, 'Beatriz', 234141, 'Rua da Liberdade nº50 6ºFt', 13341211, 1416578);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Extraindo dados da tabela `fatura`
--

INSERT INTO `fatura` (`codFatura`, `ClientecodCivil`, `DataDaFatura`, `Garantia`, `Total`) VALUES
(2, 1, '31-01-2015', 'Até 31 de janeiro', 321),
(3, 2, '25-01-2015', '3 Anos', 300),
(4, 3, '31-01-2015', '5 Anos', 190),
(6, 2, '1-02-2015', '1 Ano', 100),
(8, 1, '31-06-2012', 'Expirada', 500),
(9, 1, '27-07-2015', '2 anos', 100);

-- --------------------------------------------------------

--
-- Estrutura da tabela `fatura_produto`
--

CREATE TABLE IF NOT EXISTS `fatura_produto` (
  `FaturacodFatura` int(10) NOT NULL,
  `ProdutocodProduto` int(10) NOT NULL,
  PRIMARY KEY (`FaturacodFatura`,`ProdutocodProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `fatura_produto`
--

INSERT INTO `fatura_produto` (`FaturacodFatura`, `ProdutocodProduto`) VALUES
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(2, 7),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(2, 15),
(2, 16),
(2, 17),
(3, 2),
(3, 3),
(3, 17),
(4, 1),
(4, 2),
(4, 3),
(4, 7),
(4, 12),
(4, 17),
(6, 2),
(6, 9),
(8, 2),
(9, 10);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Extraindo dados da tabela `produto`
--

INSERT INTO `produto` (`codProduto`, `NomeProduto`, `Marca`, `DataValidade`, `Preco`, `Stock`) VALUES
(1, 'Galaxy S6', 'Samsung', 'Não Tem', '599', '5000'),
(2, 'Zenfone5', 'Asus', 'Nao Tem', '300', '2'),
(3, 'HP Mini 210', 'HP', 'Nao tem', '150', '50'),
(7, 'Spotify', 'Spotify', '3 Meses Premium', '0,99', '1000'),
(9, 'Surface 3', 'Microsoft', 'Nao tem', '900', '0'),
(10, '1life', 'c:stark', 'Nao tem', '59', '30'),
(11, 'Grand Neo Duos', 'Samsung', 'Nao tem', '190', '2000'),
(12, 'Ace 2', 'Samsung', 'Nao tem', '300', '7'),
(13, 'PSP', 'Sony', 'Não tem', '90', '3'),
(14, 'PlayStation 4', 'Sony', 'Não tem', '499', '200'),
(15, 'Nintendo 3DS', 'Nintendo', 'Não tem', '150', '990'),
(16, 'Wii U Basic Pack', 'Nintendo', 'Não tem', '199', '90'),
(17, 'Nintendo Wii', 'Nintendo', 'Não tem', '120', '100');

-- --------------------------------------------------------

--
-- Estrutura da tabela `utilizadores`
--

CREATE TABLE IF NOT EXISTS `utilizadores` (
  `user` varchar(50) NOT NULL,
  `codUtilizador` int(10) NOT NULL AUTO_INCREMENT,
  `password` varchar(90) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`codUtilizador`),
  UNIQUE KEY `codUtilizador` (`codUtilizador`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Extraindo dados da tabela `utilizadores`
--

INSERT INTO `utilizadores` (`user`, `codUtilizador`, `password`, `email`) VALUES
('sousa47', 1, '123bacalhau', 'davimfs7@gmail.com'),
('catacriste', 2, 'benfica', 'criste.cata@hotmail.com'),
('severino100', 4, 'mariana', 'rubenfranciscos@gmail.com'),
('marianaCosta', 5, 'sporting', 'naoseioemaildamariana@qualquer.coisa'),
('teste', 6, '123', 'test@123.com'),
('teste2', 7, 'benfica', 'teste2@gfdsfas.com'),
('', 8, '', '');

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
