USE [BRTADEV]
GO

/****** Object:  Table [dbo].[Material]    Script Date: 12/29/2016 4:32:48 PM ******/
DROP TABLE [dbo].[Material]
GO

/****** Object:  Table [dbo].[Material]    Script Date: 12/29/2016 4:32:48 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Material](
	[MaterialId] [int] NOT NULL,
	[Description] [nvarchar](225) NOT NULL,
	[ShortName] [nvarchar](25) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_Material] PRIMARY KEY CLUSTERED 
(
	[MaterialId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

