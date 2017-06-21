USE [BRTADEV]
GO

/****** Object:  Table [dbo].[AccessLevel]    Script Date: 12/21/2016 1:18:27 PM ******/
DROP TABLE [dbo].[AccessLevel]
GO

/****** Object:  Table [dbo].[AccessLevel]    Script Date: 12/21/2016 1:18:27 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[AccessLevel](
	[AccessId] [int] NOT NULL,
	[AccessLvlDesc] [nvarchar](50) NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_AccessId] PRIMARY KEY CLUSTERED 
(
	[AccessId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


