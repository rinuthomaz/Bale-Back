USE [BRTADEV]
GO

/****** Object:  Table [dbo].[IncidentType]    Script Date: 12/21/2016 1:57:11 PM ******/
DROP TABLE [dbo].[IncidentType]
GO

/****** Object:  Table [dbo].[IncidentType]    Script Date: 12/21/2016 1:57:11 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[IncidentType](
	[IncidentTypeId] [int] NOT NULL,
	[IncidentDescription] [nvarchar](50) NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdatedBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_IncidentType] PRIMARY KEY CLUSTERED 
(
	[IncidentTypeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

