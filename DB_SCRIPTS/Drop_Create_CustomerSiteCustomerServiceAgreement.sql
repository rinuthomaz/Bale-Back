USE [BRTADEV]
GO

ALTER TABLE [dbo].[CustomerSiteCustomerServiceAgreement] DROP CONSTRAINT [FK_CustomerSiteCustomerServiceAgreement_CustomerSite]
GO

ALTER TABLE [dbo].[CustomerSiteCustomerServiceAgreement] DROP CONSTRAINT [FK_CustomerSiteCustomerServiceAgreement_CustomerServiceAgreement]
GO

/****** Object:  Table [dbo].[CustomerSiteCustomerServiceAgreement]    Script Date: 12/29/2016 5:48:29 PM ******/
DROP TABLE [dbo].[CustomerSiteCustomerServiceAgreement]
GO

/****** Object:  Table [dbo].[CustomerSiteCustomerServiceAgreement]    Script Date: 12/29/2016 5:48:29 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CustomerSiteCustomerServiceAgreement](
	[CustomerSiteCustomerServiceAgreementId] [int] NOT NULL,
	[CustomerServiceAgreementId] [int] NOT NULL,
	[CustomerSiteId] [int] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_CustomerSiteCustomerServiceAgreement] PRIMARY KEY CLUSTERED 
(
	[CustomerSiteCustomerServiceAgreementId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[CustomerSiteCustomerServiceAgreement]  WITH CHECK ADD  CONSTRAINT [FK_CustomerSiteCustomerServiceAgreement_CustomerServiceAgreement] FOREIGN KEY([CustomerServiceAgreementId])
REFERENCES [dbo].[CustomerServiceAgreement] ([CustomerServiceAgreementId])
GO

ALTER TABLE [dbo].[CustomerSiteCustomerServiceAgreement] CHECK CONSTRAINT [FK_CustomerSiteCustomerServiceAgreement_CustomerServiceAgreement]
GO

ALTER TABLE [dbo].[CustomerSiteCustomerServiceAgreement]  WITH CHECK ADD  CONSTRAINT [FK_CustomerSiteCustomerServiceAgreement_CustomerSite] FOREIGN KEY([CustomerSiteId])
REFERENCES [dbo].[CustomerSite] ([CustomerSiteId])
GO

ALTER TABLE [dbo].[CustomerSiteCustomerServiceAgreement] CHECK CONSTRAINT [FK_CustomerSiteCustomerServiceAgreement_CustomerSite]
GO

