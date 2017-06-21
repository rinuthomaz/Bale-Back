USE [BRTADEV]
GO

ALTER TABLE [dbo].[SupplierContact] DROP CONSTRAINT [FK_SupplierContact_Supplier]
GO

ALTER TABLE [dbo].[SupplierContact] DROP CONSTRAINT [FK_SupplierContact_Contact]
GO

/****** Object:  Table [dbo].[SupplierContact]    Script Date: 12/29/2016 4:44:55 PM ******/
DROP TABLE [dbo].[SupplierContact]
GO

/****** Object:  Table [dbo].[SupplierContact]    Script Date: 12/29/2016 4:44:55 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SupplierContact](
	[SupplierContactId] [int] NOT NULL,
	[SupplierId] [int] NOT NULL,
	[ContactId] [int] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_SupplierContact] PRIMARY KEY CLUSTERED 
(
	[SupplierContactId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[SupplierContact]  WITH CHECK ADD  CONSTRAINT [FK_SupplierContact_Contact] FOREIGN KEY([ContactId])
REFERENCES [dbo].[Contact] ([ContactId])
GO

ALTER TABLE [dbo].[SupplierContact] CHECK CONSTRAINT [FK_SupplierContact_Contact]
GO

ALTER TABLE [dbo].[SupplierContact]  WITH CHECK ADD  CONSTRAINT [FK_SupplierContact_Supplier] FOREIGN KEY([SupplierId])
REFERENCES [dbo].[Supplier] ([SupplierId])
GO

ALTER TABLE [dbo].[SupplierContact] CHECK CONSTRAINT [FK_SupplierContact_Supplier]
GO

