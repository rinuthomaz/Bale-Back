USE [BRTADEV]
GO

/****** Object:  Table [dbo].[Customer]    Script Date: 12/21/2016 2:42:55 PM ******/
DROP TABLE [dbo].[Customer]
GO

/****** Object:  Table [dbo].[Customer]    Script Date: 12/21/2016 2:42:55 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Customer](
	[CustomerId] [int] NOT NULL,
	[CustomerName] [nvarchar](225) NOT NULL,
	[LegacyVendorId] [nvarchar](20) NULL,
	[CustomerStateId] [int] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[CustomerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

