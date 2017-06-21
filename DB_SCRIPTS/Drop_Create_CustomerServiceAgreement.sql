USE [BRTADEV]
GO

ALTER TABLE [dbo].[CustomerServiceAgreement] DROP CONSTRAINT [FK_CustomerServiceAgreement_ServiceAgreement]
GO

ALTER TABLE [dbo].[CustomerServiceAgreement] DROP CONSTRAINT [FK_CustomerServiceAgreement_Customer]
GO

/****** Object:  Table [dbo].[CustomerServiceAgreement]    Script Date: 12/29/2016 5:39:49 PM ******/
DROP TABLE [dbo].[CustomerServiceAgreement]
GO

/****** Object:  Table [dbo].[CustomerServiceAgreement]    Script Date: 12/29/2016 5:39:49 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CustomerServiceAgreement](
	[CustomerServiceAgreementId] [int] NOT NULL,
	[ServiceAgreementId] [int] NOT NULL,
	[CustomerId] [int] NOT NULL,
	[IsForAllSites] [int] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_CustomerServiceAgreement] PRIMARY KEY CLUSTERED 
(
	[CustomerServiceAgreementId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[CustomerServiceAgreement]  WITH CHECK ADD  CONSTRAINT [FK_CustomerServiceAgreement_Customer] FOREIGN KEY([CustomerId])
REFERENCES [dbo].[Customer] ([CustomerId])
GO

ALTER TABLE [dbo].[CustomerServiceAgreement] CHECK CONSTRAINT [FK_CustomerServiceAgreement_Customer]
GO

ALTER TABLE [dbo].[CustomerServiceAgreement]  WITH CHECK ADD  CONSTRAINT [FK_CustomerServiceAgreement_ServiceAgreement] FOREIGN KEY([ServiceAgreementId])
REFERENCES [dbo].[ServiceAgreement] ([ServiceAgreementId])
GO

ALTER TABLE [dbo].[CustomerServiceAgreement] CHECK CONSTRAINT [FK_CustomerServiceAgreement_ServiceAgreement]
GO

