USE [BRTADEV]
GO

ALTER TABLE [dbo].[SupplierSite] DROP CONSTRAINT [FK_SupplierSite_Supplier]
GO

ALTER TABLE [dbo].[SupplierSite] DROP CONSTRAINT [FK_SupplierSite_Location]
GO

/****** Object:  Table [dbo].[SupplierSite]    Script Date: 12/29/2016 4:50:35 PM ******/
DROP TABLE [dbo].[SupplierSite]
GO

/****** Object:  Table [dbo].[SupplierSite]    Script Date: 12/29/2016 4:50:35 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SupplierSite](
	[SupplierSiteId] [int] NOT NULL,
	[SupplierId] [int] NOT NULL,
	[SiteName] [nvarchar](225) NOT NULL,
	[LocationId] [int] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_SupplierSite] PRIMARY KEY CLUSTERED 
(
	[SupplierSiteId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[SupplierSite]  WITH CHECK ADD  CONSTRAINT [FK_SupplierSite_Location] FOREIGN KEY([LocationId])
REFERENCES [dbo].[Location] ([LocationId])
GO

ALTER TABLE [dbo].[SupplierSite] CHECK CONSTRAINT [FK_SupplierSite_Location]
GO

ALTER TABLE [dbo].[SupplierSite]  WITH CHECK ADD  CONSTRAINT [FK_SupplierSite_Supplier] FOREIGN KEY([SupplierId])
REFERENCES [dbo].[Supplier] ([SupplierId])
GO

ALTER TABLE [dbo].[SupplierSite] CHECK CONSTRAINT [FK_SupplierSite_Supplier]
GO

