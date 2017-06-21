USE [BRTADEV]
GO

ALTER TABLE [dbo].[InboundServiceAgreementOutboundServiceAgreement] DROP CONSTRAINT [FK_InboundServiceAgreementOutboundServiceAgreement_ServiceAgreement1]
GO

ALTER TABLE [dbo].[InboundServiceAgreementOutboundServiceAgreement] DROP CONSTRAINT [FK_InboundServiceAgreementOutboundServiceAgreement_ServiceAgreement]
GO

/****** Object:  Table [dbo].[InboundServiceAgreementOutboundServiceAgreement]    Script Date: 12/29/2016 5:45:07 PM ******/
DROP TABLE [dbo].[InboundServiceAgreementOutboundServiceAgreement]
GO

/****** Object:  Table [dbo].[InboundServiceAgreementOutboundServiceAgreement]    Script Date: 12/29/2016 5:45:07 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[InboundServiceAgreementOutboundServiceAgreement](
	[InboundServiceAgreementOutboundServiceAgreementId] [int] NOT NULL,
	[InboundServiceAgreementId] [int] NOT NULL,
	[OutboundServiceAgreementId] [int] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_InboundServiceAgreementOutboundServiceAgreement] PRIMARY KEY CLUSTERED 
(
	[InboundServiceAgreementOutboundServiceAgreementId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[InboundServiceAgreementOutboundServiceAgreement]  WITH CHECK ADD  CONSTRAINT [FK_InboundServiceAgreementOutboundServiceAgreement_ServiceAgreement] FOREIGN KEY([InboundServiceAgreementId])
REFERENCES [dbo].[ServiceAgreement] ([ServiceAgreementId])
GO

ALTER TABLE [dbo].[InboundServiceAgreementOutboundServiceAgreement] CHECK CONSTRAINT [FK_InboundServiceAgreementOutboundServiceAgreement_ServiceAgreement]
GO

ALTER TABLE [dbo].[InboundServiceAgreementOutboundServiceAgreement]  WITH CHECK ADD  CONSTRAINT [FK_InboundServiceAgreementOutboundServiceAgreement_ServiceAgreement1] FOREIGN KEY([OutboundServiceAgreementId])
REFERENCES [dbo].[ServiceAgreement] ([ServiceAgreementId])
GO

ALTER TABLE [dbo].[InboundServiceAgreementOutboundServiceAgreement] CHECK CONSTRAINT [FK_InboundServiceAgreementOutboundServiceAgreement_ServiceAgreement1]
GO

