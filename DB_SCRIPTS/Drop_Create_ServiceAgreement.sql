USE [BRTADEV]
GO

ALTER TABLE [dbo].[ServiceAgreement] DROP CONSTRAINT [FK_ServiceAgreement_Service]
GO

ALTER TABLE [dbo].[ServiceAgreement] DROP CONSTRAINT [FK_ServiceAgreement_Material]
GO

/****** Object:  Table [dbo].[ServiceAgreement]    Script Date: 12/29/2016 5:37:28 PM ******/
DROP TABLE [dbo].[ServiceAgreement]
GO

/****** Object:  Table [dbo].[ServiceAgreement]    Script Date: 12/29/2016 5:37:28 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ServiceAgreement](
	[ServiceAgreementId] [int] NOT NULL,
	[PrimaryServiceId] [int] NULL,
	[MaterialId] [int] NULL,
	[Description] [nvarchar](225) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_ServiceAgreement] PRIMARY KEY CLUSTERED 
(
	[ServiceAgreementId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[ServiceAgreement]  WITH CHECK ADD  CONSTRAINT [FK_ServiceAgreement_Material] FOREIGN KEY([MaterialId])
REFERENCES [dbo].[Material] ([MaterialId])
GO

ALTER TABLE [dbo].[ServiceAgreement] CHECK CONSTRAINT [FK_ServiceAgreement_Material]
GO

ALTER TABLE [dbo].[ServiceAgreement]  WITH CHECK ADD  CONSTRAINT [FK_ServiceAgreement_Service] FOREIGN KEY([PrimaryServiceId])
REFERENCES [dbo].[Service] ([ServiceId])
GO

ALTER TABLE [dbo].[ServiceAgreement] CHECK CONSTRAINT [FK_ServiceAgreement_Service]
GO

