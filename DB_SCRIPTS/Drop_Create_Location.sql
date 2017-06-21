USE [BRTADEV]
GO

/****** Object:  Table [dbo].[Location]    Script Date: 12/21/2016 2:47:05 PM ******/
DROP TABLE [dbo].[Location]
GO

/****** Object:  Table [dbo].[Location]    Script Date: 12/21/2016 2:47:05 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Location](
	[LocationId] [int] NOT NULL,
	[HouseNumber] [nvarchar](255) NULL,
	[Address1] [nvarchar](255) NULL,
	[Address2] [nvarchar](255) NULL,
	[Address3] [nvarchar](50) NULL,
	[Address4] [nvarchar](50) NULL,
	[Address5] [nvarchar](50) NULL,
	[Postcode] [nvarchar](12) NULL,
	[TelNo] [nvarchar](30) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_Location] PRIMARY KEY CLUSTERED 
(
	[LocationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

