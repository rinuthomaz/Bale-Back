USE [BRTADEV]
GO

ALTER TABLE [dbo].[ServiceAgreementPrice] DROP CONSTRAINT [FK_ServiceAgreementPrice_ServiceAgreement]
GO

ALTER TABLE [dbo].[ServiceAgreementPrice] DROP CONSTRAINT [FK_ServiceAgreementPrice_Price]
GO

/****** Object:  Table [dbo].[ServiceAgreementPrice]    Script Date: 12/29/2016 5:42:31 PM ******/
DROP TABLE [dbo].[ServiceAgreementPrice]
GO

/****** Object:  Table [dbo].[ServiceAgreementPrice]    Script Date: 12/29/2016 5:42:31 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ServiceAgreementPrice](
	[ServiceAgreementPriceId] [int] NOT NULL,
	[ServiceAgreementId] [int] NOT NULL,
	[PriceId] [int] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_ServiceAgreementPrice] PRIMARY KEY CLUSTERED 
(
	[ServiceAgreementPriceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[ServiceAgreementPrice]  WITH CHECK ADD  CONSTRAINT [FK_ServiceAgreementPrice_Price] FOREIGN KEY([PriceId])
REFERENCES [dbo].[Price] ([PriceId])
GO

ALTER TABLE [dbo].[ServiceAgreementPrice] CHECK CONSTRAINT [FK_ServiceAgreementPrice_Price]
GO

ALTER TABLE [dbo].[ServiceAgreementPrice]  WITH CHECK ADD  CONSTRAINT [FK_ServiceAgreementPrice_ServiceAgreement] FOREIGN KEY([ServiceAgreementId])
REFERENCES [dbo].[ServiceAgreement] ([ServiceAgreementId])
GO

ALTER TABLE [dbo].[ServiceAgreementPrice] CHECK CONSTRAINT [FK_ServiceAgreementPrice_ServiceAgreement]
GO

