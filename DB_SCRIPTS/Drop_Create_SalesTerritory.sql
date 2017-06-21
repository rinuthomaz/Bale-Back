USE [BRTADEV]
GO

/****** Object:  Table [dbo].[SalesTerritory]    Script Date: 12/29/2016 4:22:38 PM ******/
DROP TABLE [dbo].[SalesTerritory]
GO

/****** Object:  Table [dbo].[SalesTerritory]    Script Date: 12/29/2016 4:22:38 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SalesTerritory](
	[SalesTerritoryId] [int] NOT NULL,
	[Description] [nvarchar](60) NOT NULL,
	[FullDescription] [nvarchar](100) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_SalesTerritory] PRIMARY KEY CLUSTERED 
(
	[SalesTerritoryId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

