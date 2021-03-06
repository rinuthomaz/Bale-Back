USE [BRTADEV]
GO

/****** Object:  Table [dbo].[Days]    Script Date: 12/29/2016 4:16:51 PM ******/
DROP TABLE [dbo].[Days]
GO

/****** Object:  Table [dbo].[Days]    Script Date: 12/29/2016 4:16:51 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Days](
	[WeekDayId] [int] NOT NULL,
	[WeekDay] [nvarchar](20) NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_Days] PRIMARY KEY CLUSTERED 
(
	[WeekDayId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

