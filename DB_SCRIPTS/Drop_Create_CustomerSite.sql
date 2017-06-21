USE [BRTADEV]
GO

ALTER TABLE [dbo].[CustomerSite] DROP CONSTRAINT [FK_CustomerSite_SysUser1]
GO

ALTER TABLE [dbo].[CustomerSite] DROP CONSTRAINT [FK_CustomerSite_SysUser]
GO

ALTER TABLE [dbo].[CustomerSite] DROP CONSTRAINT [FK_CustomerSite_SalesTerritory]
GO

ALTER TABLE [dbo].[CustomerSite] DROP CONSTRAINT [FK_CustomerSite_Location]
GO

ALTER TABLE [dbo].[CustomerSite] DROP CONSTRAINT [FK_CustomerSite_CustomerId]
GO

/****** Object:  Table [dbo].[CustomerSite]    Script Date: 12/29/2016 5:33:44 PM ******/
DROP TABLE [dbo].[CustomerSite]
GO

/****** Object:  Table [dbo].[CustomerSite]    Script Date: 12/29/2016 5:33:44 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CustomerSite](
	[CustomerSiteId] [int] NOT NULL,
	[CustomerId] [int] NOT NULL,
	[SiteName] [nvarchar](50) NOT NULL,
	[LocationId] [int] NOT NULL,
	[SalesTerritoryId] [int] NULL,
	[CustomerServiceRepId] [int] NULL,
	[SalesRepId] [int] NULL,
	[SysUserName] [nvarchar](225) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_CustomerSite1] PRIMARY KEY CLUSTERED 
(
	[CustomerSiteId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[CustomerSite]  WITH CHECK ADD  CONSTRAINT [FK_CustomerSite_CustomerId] FOREIGN KEY([CustomerId])
REFERENCES [dbo].[Customer] ([CustomerId])
GO

ALTER TABLE [dbo].[CustomerSite] CHECK CONSTRAINT [FK_CustomerSite_CustomerId]
GO

ALTER TABLE [dbo].[CustomerSite]  WITH CHECK ADD  CONSTRAINT [FK_CustomerSite_Location] FOREIGN KEY([LocationId])
REFERENCES [dbo].[Location] ([LocationId])
GO

ALTER TABLE [dbo].[CustomerSite] CHECK CONSTRAINT [FK_CustomerSite_Location]
GO

ALTER TABLE [dbo].[CustomerSite]  WITH CHECK ADD  CONSTRAINT [FK_CustomerSite_SalesTerritory] FOREIGN KEY([SalesTerritoryId])
REFERENCES [dbo].[SalesTerritory] ([SalesTerritoryId])
GO

ALTER TABLE [dbo].[CustomerSite] CHECK CONSTRAINT [FK_CustomerSite_SalesTerritory]
GO

ALTER TABLE [dbo].[CustomerSite]  WITH CHECK ADD  CONSTRAINT [FK_CustomerSite_SysUser] FOREIGN KEY([CustomerServiceRepId])
REFERENCES [dbo].[SysUser] ([SysUserId])
GO

ALTER TABLE [dbo].[CustomerSite] CHECK CONSTRAINT [FK_CustomerSite_SysUser]
GO

ALTER TABLE [dbo].[CustomerSite]  WITH CHECK ADD  CONSTRAINT [FK_CustomerSite_SysUser1] FOREIGN KEY([SalesRepId])
REFERENCES [dbo].[SysUser] ([SysUserId])
GO

ALTER TABLE [dbo].[CustomerSite] CHECK CONSTRAINT [FK_CustomerSite_SysUser1]
GO

