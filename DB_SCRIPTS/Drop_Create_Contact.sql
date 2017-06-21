USE [BRTADEV]
GO

/****** Object:  Table [dbo].[Contact]    Script Date: 12/29/2016 4:25:53 PM ******/
DROP TABLE [dbo].[Contact]
GO

/****** Object:  Table [dbo].[Contact]    Script Date: 12/29/2016 4:25:53 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Contact](
	[ContactId] [int] NOT NULL,
	[ForeName] [nvarchar](50) NOT NULL,
	[Surname] [nvarchar](50) NULL,
	[JobTitle] [nvarchar](50) NULL,
	[TelNo] [nvarchar](30) NULL,
	[TelExtension] [nvarchar](15) NULL,
	[OtherTelNo] [nvarchar](30) NULL,
	[FaxNo] [nvarchar](30) NULL,
	[Email] [nvarchar](254) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_Contact] PRIMARY KEY CLUSTERED 
(
	[ContactId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

